#!/bin/bash
# Easy push script for EGTI project on Linux Fedora.
# Usage: ./push_to_github_easy.sh [GITHUB_USERNAME] [REPO_NAME]
# If arguments are omitted, you will be prompted.

set -e

# Function to prompt for input
prompt() {
    local prompt_text="$1"
    local var_name="$2"
    local default_value="$3"
    if [ -n "$default_value" ]; then
        read -p "$prompt_text [$default_value]: " input
        eval "$var_name=\${input:-\$default_value}"
    else
        read -p "$prompt_text: " input
        eval "$var_name=\"\$input\""
    fi
}

# Get GitHub username from argument or prompt
if [ -n "$1" ]; then
    GITHUB_USERNAME="$1"
else
    prompt "Enter your GitHub username" GITHUB_USERNAME "kmagwenzi"
fi

# Get repository name from argument or prompt
if [ -n "$2" ]; then
    REPO_NAME="$2"
else
    prompt "Enter the repository name" REPO_NAME "EGTI"
fi

REMOTE_URL="https://github.com/${GITHUB_USERNAME}/${REPO_NAME}.git"

echo "=== Pushing EGTI project to GitHub ==="
echo "Remote URL: $REMOTE_URL"
echo

# Check if remote 'origin' exists, add or update
if git remote | grep -q origin; then
    echo "Remote 'origin' already exists. Updating URL..."
    git remote set-url origin "$REMOTE_URL"
else
    echo "Adding remote 'origin'..."
    git remote add origin "$REMOTE_URL"
fi

echo "Pushing to GitHub (branch: main)..."
if git push -u origin main; then
    echo "✅ Success! Repository pushed to $REMOTE_URL"
    echo "You can view it at: https://github.com/${GITHUB_USERNAME}/${REPO_NAME}"
else
    echo "❌ Push failed. Common reasons:"
    echo "   1. Repository '$REPO_NAME' does not exist on GitHub."
    echo "      Create it at: https://github.com/new (do NOT initialize with README, .gitignore, or license)"
    echo "   2. Authentication failed (use HTTPS with credentials or configure SSH keys)."
    echo "   3. Network issues."
    exit 1
fi