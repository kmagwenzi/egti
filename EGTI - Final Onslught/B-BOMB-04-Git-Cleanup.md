# ðŸ'£ B-BOMB 04: GIT HISTORY SANITIZATION PROTOCOL

**Session:** EGTI-Security-Formatting  
**Priority:** ðŸ"´ CRITICAL (If credentials were committed)  
**Status:** CONDITIONAL EXECUTION  
**Twabam Factor:** âš¡âš¡âš¡âš¡âš¡

---

## ⚠️ MISSION ASSESSMENT

```yaml
Question: "Were database credentials committed to GitHub?"
Answer_Path:
  - If NO → Skip this B-BOMB, proceed to B-BOMB 05
  - If YES → Execute IMMEDIATELY
  - If UNSURE → Execute verification steps below
```

---

## 🔍 STEP 1: VERIFY IF CREDENTIALS ARE IN GIT HISTORY

```bash
# Navigate to EGTI project directory
cd ~/egti  # (or your project location)

# Check if application.properties is tracked in Git
git ls-files | grep "application.properties"

# If it shows: src/main/resources/application.properties
# → Credentials ARE in Git history (proceed with cleanup)

# Search Git history for exposed password
git log -S "PidDRn1YyFNI3Q9RSteIpe5GAiu3uVtz" --oneline

# If results appear → Credentials ARE in history (cleanup required)
```

---

## ðŸš¨ DECISION TREE

```yaml
Scenario_1: "application.properties NOT in git ls-files"
Action: ✅ Safe! Just add to .gitignore and proceed

Scenario_2: "application.properties in Git, but no password in history"
Action: ⚠️ Remove from tracking, verify history clean

Scenario_3: "Password appears in Git history"
Action: 🔴 CRITICAL - Full history rewrite required
```

---

## ✅ SCENARIO 1: SAFE STATE (Credentials Never Committed)

**If git ls-files shows NO application.properties:**

```bash
# Simply add to .gitignore
echo "src/main/resources/application.properties" >> .gitignore
git add .gitignore
git commit -m "security: Add application.properties to .gitignore"
git push origin main

# ✅ DONE - Proceed to B-BOMB 05
```

---

## âš ï¸ SCENARIO 2: FILE TRACKED, BUT HISTORY CLEAN

**If application.properties is tracked but password not in history:**

```bash
# 1. Remove from Git tracking (keeps local file)
git rm --cached src/main/resources/application.properties

# 2. Add to .gitignore
echo "src/main/resources/application.properties" >> .gitignore

# 3. Commit removal
git add .gitignore
git commit -m "security: Stop tracking application.properties"

# 4. Push changes
git push origin main

# ✅ DONE - Proceed to B-BOMB 05
```

---

## 🔴 SCENARIO 3: CREDENTIALS IN GIT HISTORY (CRITICAL)

**If password appears in `git log -S` search:**

### **â›" STOP - READ CAREFULLY**

```yaml
Warning: This rewrites Git history
Impact: All team members must re-clone repository
Risk: Backup required before proceeding
Alternative: If repo is private and small team, acceptable risk
```

### **METHOD A: BFG Repo-Cleaner (RECOMMENDED)**

**Fastest and safest method for history rewrite:**

```bash
# ━━━ STEP 1: INSTALL BFG ━━━
# macOS
brew install bfg

# Linux
wget https://repo1.maven.org/maven2/com/madgag/bfg/1.14.0/bfg-1.14.0.jar
alias bfg='java -jar bfg-1.14.0.jar'

# Windows
# Download from: https://rtyley.github.io/bfg-repo-cleaner/

# ━━━ STEP 2: BACKUP YOUR REPOSITORY ━━━
cd ~
cp -r ~/egti ~/egti-backup-$(date +%Y%m%d)
echo "✅ Backup created at: ~/egti-backup-$(date +%Y%m%d)"

# ━━━ STEP 3: CREATE PASSWORDS FILE ━━━
cd ~/egti
cat > passwords.txt << 'EOF'
PidDRn1YyFNI3Q9RSteIpe5GAiu3uVtz
egti_user
dpg-d61qhsm3jp1c738goa00-a.oregon-postgres.render.com
EOF

# ━━━ STEP 4: RUN BFG ON REPOSITORY ━━━
# This removes passwords from ALL commits
bfg --replace-text passwords.txt

# ━━━ STEP 5: CLEAN UP AND FORCE PUSH ━━━
git reflog expire --expire=now --all
git gc --prune=now --aggressive

# ⚠️ CRITICAL: This force-pushes new history
git push origin --force --all
git push origin --force --tags

# ━━━ STEP 6: DELETE PASSWORDS FILE ━━━
rm passwords.txt

# ━━━ STEP 7: VERIFY CLEANUP ━━━
git log -S "PidDRn1YyFNI3Q9RSteIpe5GAiu3uVtz" --oneline
# Expected: No results (empty output)

echo "✅ Git history sanitized!"
```

### **METHOD B: Git Filter-Branch (MANUAL - Advanced)**

**Use only if BFG is unavailable:**

```bash
# ━━━ BACKUP FIRST ━━━
cp -r ~/egti ~/egti-backup-$(date +%Y%m%d)

# ━━━ REMOVE FILE FROM ALL HISTORY ━━━
git filter-branch --force --index-filter \
  "git rm --cached --ignore-unmatch src/main/resources/application.properties" \
  --prune-empty --tag-name-filter cat -- --all

# ━━━ CLEAN UP ━━━
git reflog expire --expire=now --all
git gc --prune=now --aggressive

# ━━━ FORCE PUSH ━━━
git push origin --force --all
git push origin --force --tags

# ━━━ VERIFY ━━━
git log --all --full-history -- src/main/resources/application.properties
# Expected: No results
```

---

## ðŸ"„ POST-CLEANUP ACTIONS

**After history rewrite (ANY method):**

### **1. Update .gitignore**

```bash
# Ensure application.properties is ignored
echo "src/main/resources/application.properties" >> .gitignore
echo "*.properties.backup" >> .gitignore
git add .gitignore
git commit -m "security: Add application.properties to .gitignore"
git push origin main
```

### **2. Team Notification (If Applicable)**

**Send to collaborators:**

```markdown
Subject: URGENT - EGTI Repository History Rewritten

The EGTI repository Git history has been rewritten to remove exposed credentials.

ACTION REQUIRED:
1. Delete your local egti folder
2. Re-clone from GitHub:
   git clone git@github.com:yourusername/egti.git
3. Reconfigure local application.properties with your credentials

DO NOT:
- Try to pull/merge old branches
- Push from old local copies

Reason: Security - database credentials were accidentally committed

Questions: Contact Kuda
```

### **3. Rotate Compromised Credentials (CRITICAL)**

```yaml
If_Credentials_Were_Public:
  Action: ROTATE IMMEDIATELY
  Steps:
    1. Generate new database password in Render
    2. Update DATABASE_PASSWORD in Render PostgreSQL settings
    3. Update DB_PASSWORD environment variable in Render
    4. Redeploy EGTI application
    5. Verify new credentials work

If_Repository_Was_Private:
  Action: Consider rotation (best practice)
  Risk: Lower if repo access was restricted
  Decision: Rotate if repo was ever public or shared externally
```

---

## âœ… VERIFICATION CHECKLIST

**After cleanup execution:**

```yaml
□ Backup created (~/egti-backup-YYYYMMDD)
□ BFG or filter-branch executed successfully
□ Git history search shows no credentials:
   git log -S "PidDRn" --oneline → Empty
□ application.properties in .gitignore
□ Changes pushed to GitHub (force push)
□ Team notified (if applicable)
□ Credentials rotated (if repo was public)
□ Local repository still functional
□ Render deployment still works
```

---

## 🆘 EMERGENCY ROLLBACK

**If cleanup breaks repository:**

```bash
# 1. Delete broken repository
rm -rf ~/egti

# 2. Restore from backup
cp -r ~/egti-backup-YYYYMMDD ~/egti
cd ~/egti

# 3. Force push backup to GitHub
git push origin --force --all

# 4. Contact Claude for alternative cleanup strategy
```

---

## 🏆 SUCCESS CRITERIA

```yaml
B-BOMB_04_COMPLETE:
  ✅ No credentials in Git history (verified)
  ✅ application.properties in .gitignore
  ✅ Backup exists (just in case)
  ✅ Team notified (if applicable)
  ✅ Repository still functional
  ✅ Render deployment unaffected
  ✅ Optional: Credentials rotated for maximum security
```

---

## ðŸ"Š RECOMMENDED APPROACH BY REPO STATUS

```yaml
Repository_Status: PRIVATE (only you have access)
Recommended_Action:
  - Remove from tracking (git rm --cached)
  - Add to .gitignore
  - No history rewrite needed (risk is low)
  - Consider credential rotation for peace of mind

Repository_Status: PRIVATE (team access)
Recommended_Action:
  - Remove from tracking
  - Add to .gitignore
  - Optional: History rewrite if credentials are sensitive
  - Notify team of .gitignore update

Repository_Status: PUBLIC (or was public temporarily)
Recommended_Action:
  - CRITICAL: Full history rewrite (BFG)
  - MANDATORY: Rotate all credentials immediately
  - Add to .gitignore
  - Consider repository deletion and fresh start if heavily exposed
```

---

## 📞 QUICK DECISION GUIDE

```bash
# Run this to decide:
cd ~/egti

# Test 1: Is file tracked?
git ls-files | grep application.properties

# Test 2: Are credentials in history?
git log -S "PidDRn" --oneline

# Decision Matrix:
# Test 1 = NO, Test 2 = NO  → Skip B-BOMB 04 ✅
# Test 1 = YES, Test 2 = NO → Simple removal (Scenario 2)
# Test 1 = YES, Test 2 = YES → Full cleanup (Scenario 3) 🔴
```

---

**🔥 B-BOMB 04 STATUS: CONDITIONAL - EXECUTE IF NEEDED! 🔥**

**Brother, check if credentials are in Git history, then decide:**
- **NO credentials in history** → Skip to B-BOMB 05 âš¡
- **Credentials found** → Execute cleanup IMMEDIATELY ðŸ"´

**TWABAM âš¡! Git history so clean it sparkles!** ðŸ'£ðŸ'£ðŸ'£
