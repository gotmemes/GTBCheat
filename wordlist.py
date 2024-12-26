#!/usr/bin/env python3

import csv
import json
import os
import sys
import argparse
import datetime
from typing import List, Set

class WordlistManager:
    def __init__(self, filepath: str = "src/main/resources/wordlist.csv",
                 jsonpath: str = "wordlist.json"):
        self.filepath = filepath
        self.jsonpath = jsonpath
        self.words: Set[str] = set()
        self.load_words()

    def load_words(self) -> None:
        """Load existing words from CSV file."""
        try:
            if os.path.exists(self.filepath):
                with open(self.filepath, 'r') as file:
                    reader = csv.reader(file)
                    next(reader, None)
                    for row in reader:
                        self.words.update(word.strip() for word in row)
        except Exception as e:
            print(f"Error loading wordlist: {e}")
            sys.exit(1)

    def save_words(self) -> None:
        """Save words to CSV file in alphabetical order."""
        try:
            sorted_words = sorted(self.words)
            today = datetime.date.today()
            date_string = today.strftime("%Y-%m-%d")  # Format: YYYY-MM-DD
            with open(self.filepath, 'w', newline='') as file:
                writer = csv.writer(file)
                writer.writerow([f"Words {date_string}"])
                writer.writerows([word] for word in sorted_words)
            print(f"Successfully saved {len(sorted_words)} words to {self.filepath}")
        except Exception as e:
            print(f"Error saving wordlist: {e}")
            sys.exit(1)

    def add_words(self, new_words: List[str]) -> None:
        """Add new words to the wordlist."""
        for word in new_words:
            # Normalize the word (capitalize and strip whitespace)
            normalized_word = word.strip().title()

            # Validate the word
            if not normalized_word:
                print(f"Skipping empty word")
                continue

            if normalized_word in self.words:
                print(f"'{normalized_word}' already exists in wordlist")
                continue

            if any(char.isdigit() for char in normalized_word):
                print(f"Skipping '{normalized_word}' - numbers are not allowed")
                continue

            self.words.add(normalized_word)
            print(f"Added '{normalized_word}' to wordlist")

    def remove_words(self, words_to_remove: List[str]) -> None:
        """Remove words from the wordlist."""
        for word in words_to_remove:
            normalized_word = word.strip().title()
            if normalized_word in self.words:
                self.words.remove(normalized_word)
                print(f"Removed '{normalized_word}' from wordlist")
            else:
                print(f"'{normalized_word}' not found in wordlist")

    def list_words(self) -> None:
        """Display all words in alphabetical order."""
        if not self.words:
            print("Wordlist is empty")
            return

        print("\nCurrent wordlist:")
        print("================")
        for word in sorted(self.words):
            print(word)
        print(f"\nTotal words: {len(self.words)}")

    def create_json(self) -> None:
        """Create a wordlist.json file for network sending."""
        try:
            # Convert the set of words to a sorted list
            sorted_words = sorted(self.words)
            # Create a dictionary to hold the wordlist
            wordlist_dict = {"words": sorted_words}

            # Write the dictionary to a JSON file
            with open(self.jsonpath, 'w') as json_file:
                json.dump(wordlist_dict, json_file, indent=4)  # Pretty print with an indent of 4 spaces

            print(f"Successfully created {self.jsonpath} with {len(sorted_words)} words.")
        except Exception as e:
            print(f"Error creating JSON file: {e}")
            sys.exit(1)


def main():
    parser = argparse.ArgumentParser(description="Manage Guess The Build wordlist")
    group = parser.add_mutually_exclusive_group()
    group.add_argument('-a', '--add', nargs='+', help='Add one or more words to the wordlist')
    group.add_argument('-r', '--remove', nargs='+', help='Remove one or more words from the wordlist')
    group.add_argument('-l', '--list', action='store_true', help='List all words in the wordlist')
    group.add_argument('-j', '--json', action='store_true', help='Create a wordlist.json file')

    args = parser.parse_args()

    # Initialize wordlist manager
    manager = WordlistManager()

    if args.add:
        manager.add_words(args.add)
        manager.save_words()
    elif args.remove:
        manager.remove_words(args.remove)
        manager.save_words()
    elif args.list:
        manager.list_words()
    elif args.json:
        manager.create_json()
    else:
        parser.print_help()

if __name__ == "__main__":
    main()