import os
import json
import matplotlib.pyplot as plt
import matplotlib.ticker as ticker
import questionary
import sys

# Liste les fichiers JSON dans le dossier courant
json_files = [f for f in os.listdir('.') if f.endswith('.json')]
json_files.sort()

print(json_files)
# Si aucun fichier json, quitter
if not json_files:
    print("Aucun fichier .json trouvé dans le dossier courant.")
    sys.exit(1)

# Interface TUI pour choisir le fichier
selected_file = questionary.select(
    "Choisissez un fichier JSON de benchmark :",
    choices=json_files
).ask()

if not selected_file:
    print("Aucun fichier sélectionné. Annulation.")
    sys.exit(1)

# Chargement du fichier sélectionné
with open(selected_file, "r", encoding="utf-8") as f:
    data = json.load(f)

# Extraction des données
throughput = []
avg_time = []

for entry in data:
    name = entry["benchmark"].split(".")[-1]
    mode = entry["mode"]
    score = entry["primaryMetric"]["score"]
    unit = entry["primaryMetric"]["scoreUnit"]

    if mode == "thrpt":
        throughput.append((name, score, unit))
    elif mode == "avgt":
        avg_time.append((name, score, unit))

# Trier les données
# throughput.sort(key=lambda x: x[1], reverse=True)
# avg_time.sort(key=lambda x: x[1])

tp_names, tp_values, tp_units = zip(*throughput) if throughput else ([], [], [])
avgt_names, avgt_values, avgt_units = zip(*avg_time) if avg_time else ([], [], [])

# Définir le style
plt.style.use("seaborn-v0_8")

fig, (ax1, ax2) = plt.subplots(1, 2, figsize=(18, 8))
fig.suptitle(f"Résultats des benchmarks JMH - {selected_file}", fontsize=20, fontweight='bold')

# Couleurs
color_thrpt = "#69b3a2"
color_avgt = "#e76f51"

# Graph Throughput
bars1 = ax1.barh(tp_names, tp_values, color=color_thrpt)
ax1.set_title("Throughput (opérations/ms)", fontsize=14)
ax1.set_xlabel(tp_units[0] if tp_units else "", fontsize=12)
ax1.invert_yaxis()
ax1.grid(axis="x", linestyle="--", alpha=0.7)
ax1.xaxis.set_major_locator(ticker.MaxNLocator(5))
for bar in bars1:
    width = bar.get_width()
    ax1.text(width + 0.01, bar.get_y() + bar.get_height() / 2, f"{width:.2f}", va='center')

# Graph Avg Time
bars2 = ax2.barh(avgt_names, avgt_values, color=color_avgt)
ax2.set_title("Temps moyen par opération (ms/op)", fontsize=14)
ax2.set_xlabel(avgt_units[0] if avgt_units else "", fontsize=12)
ax2.invert_yaxis()
ax2.grid(axis="x", linestyle="--", alpha=0.7)
ax2.xaxis.set_major_locator(ticker.MaxNLocator(5))
for bar in bars2:
    width = bar.get_width()
    ax2.text(width + 0.01, bar.get_y() + bar.get_height() / 2, f"{width:.2f}", va='center')

plt.tight_layout(rect=[0, 0.03, 1, 0.93])
plt.show()
