import json
import argparse
import sys
from pathlib import Path

def load_json_file(filepath):
    with open(filepath, 'r', encoding='utf-8') as f:
        return json.load(f)

def save_json_file(data, filepath):
    with open(filepath, 'w', encoding='utf-8') as f:
        json.dump(data, f, indent=4, ensure_ascii=False)

def clean_data(data, keys_to_remove):
    return [
        {k: v for k, v in item.items() if k not in keys_to_remove}
        for item in data
    ]

def output_file_name(count: int) -> str:
    return f"{count}.json"

def main():
    parser = argparse.ArgumentParser(description="Nettoie un fichier JSON.")
    parser.add_argument("--count", type=int, default=None, help="Nombre d'éléments à garder.")

    args = parser.parse_args()

    INPUT_FILE = "raw_data.json"
    REMOVE_KEYS = ["shortDescription", "linkedConcepts", "flashcards", "base", "atomic"]


    try:
        data = load_json_file(INPUT_FILE)
        if not isinstance(data, list):
            print("Le fichier JSON doit contenir une liste d'éléments.", file=sys.stderr)
            sys.exit(1)
    except Exception as e:
        print(f"Erreur lors de la lecture du fichier : {e}", file=sys.stderr)
        sys.exit(1)

    cleaned_data = clean_data(data, REMOVE_KEYS)

    if args.count:
        cleaned_data = cleaned_data[:args.count]

    save_json_file(cleaned_data, output_file_name(args.count))
    print(f"{len(cleaned_data)} éléments sauvegardés dans '{output_file_name(args.count)}'.")

if __name__ == "__main__":
    main()
