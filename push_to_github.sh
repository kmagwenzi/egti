#!/bin/bash
# Script to push the EGTI project to GitHub.
# Before running, edit the variables below with your GitHub username and repository name.

GITHUB_USERNAME="kmagwenzi"
REPO_NAME="EGTI"

# Remote URL
REMOTE_URL="https://github.com/${GITHUB_USERNAME}/${REPO_NAME}.git"

echo "Setting remote to: ${REMOTE_URL}"
git remote add origin ${REMOTE_URL} 2>/dev/null

# If remote already exists, update URL
if [ $? -ne 0 ]; then
    echo "Remote 'origin' already exists. Updating URL..."
    git remote set-url origin ${REMOTE_URL}
fi

echo "Pushing to GitHub..."
git push -u origin main

if [ $? -eq 0 ]; then
    echo "Success! Repository pushed to ${REMOTE_URL}"
else
    echo "Push failed. Please ensure:"
    echo "1. The repository '${REPO_NAME}' exists on GitHub (create it at https://github.com/new)."
    echo "2. You have proper permissions (if the repository is private)."
    echo "3. Your GitHub credentials are configured (use HTTPS or SSH)."
    exit 1
fi