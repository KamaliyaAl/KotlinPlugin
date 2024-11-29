# Kotlin File Statistics Plugin

A lightweight IntelliJ IDEA plugin written in Kotlin that provides insightful statistics about the currently open Kotlin file. The plugin introduces a **Tool Window** with a simple yet powerful functionality: analyze and display file statistics with a single click.

## Features

- **File Length (Total Lines):** Displays the total number of lines in the open Kotlin file.
- **File Length (Non-Empty Lines):** Calculates the total number of lines, excluding blank lines.
- **Longest Function:** Identifies and displays the length (in lines) of the longest function in the file.
- **TODO Count:** Counts and displays the number of TODO markers in the file.

## How It Works

1. A **Tool Window** is added to the IntelliJ IDEA UI.
2. The Tool Window contains an icon. Click it to generate statistics for the open Kotlin file.
3. Results are displayed in a clear and concise format, providing immediate insights.

## Code Structure

The plugin is implemented in three main classes:

1. **`MyPanel`**  

2. **`MyToolWindowFactory`**  

3. **`ShowStatisticsAction`**  

## Installation

1. Clone this repository to your local machine:
   ```bash
   git clone https://github.com/your-repo/kotlin-file-statistics-plugin.git
2. Open the project in IntelliJ IDEA.
3. Build and run the plugin.
