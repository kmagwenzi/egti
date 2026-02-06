# ðŸ'£ B-BOMB 03: RENDER ENVIRONMENT VARIABLES CONFIGURATION

**Session:** EGTI-Security-Formatting  
**Priority:** ðŸ"´ CRITICAL  
**Status:** EXECUTE IMMEDIATELY  
**Twabam Factor:** âš¡âš¡âš¡âš¡âš¡

---

## ðŸŽ¯ MISSION: SECURE CREDENTIAL MIGRATION

```yaml
Current_State: "Credentials EXPOSED in application.properties"
Target_State: "All credentials secured in Render environment variables"
Impact: "Eliminates database compromise risk from Git exposure"
```

---

## 🔐 STEP 1: RENDER DASHBOARD ACCESS

**Navigate to:** https://dashboard.render.com

**Steps:**
1. Log into your Render account
2. Select the **EGTI** service
3. Click **Environment** tab in left sidebar

---

## âš¡ STEP 2: CONFIGURE ENVIRONMENT VARIABLES

### **🔑 EXTRACTED CREDENTIALS (From Current Config)**

**Copy these EXACT values into Render:**

```bash
# ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
# DATABASE CONFIGURATION
# ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Variable: DATABASE_URL
# Value: jdbc:postgresql://[REDACTED]:5432/egti
DATABASE_URL=jdbc:postgresql://[REDACTED]:5432/egti

# Variable: DB_USERNAME
# Value: [REDACTED]
DB_USERNAME=[REDACTED]

# Variable: DB_PASSWORD
# Value: [REDACTED]
DB_PASSWORD=[REDACTED]

# ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
# ADMIN CREDENTIALS (GENERATE NEW SECURE PASSWORDS)
# ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Variable: ADMIN_USERNAME
# Value: bishop (or keep existing, recommend changing for security)
ADMIN_USERNAME=bishop

# Variable: ADMIN_PASSWORD
# ⚠️ GENERATE NEW STRONG PASSWORD (see below)
ADMIN_PASSWORD=[GENERATE_STRONG_PASSWORD_HERE]
```

---

## 🔑 STEP 3: GENERATE STRONG ADMIN PASSWORD

**Execute in terminal (Warp):**

```bash
# Method 1: OpenSSL Random Generator
openssl rand -base64 20 | tr -d "=+/" | cut -c1-20

# Method 2: Mac/Linux urandom
cat /dev/urandom | LC_ALL=C tr -dc 'A-Za-z0-9!@#$%^&*' | head -c 20

# Example Output: 7K9mP2nQ7vL4jRxwT3yF
```

**Alternative:** Use 1Password/Bitwarden Password Generator
- Length: 20 characters
- Include: Uppercase, lowercase, numbers, symbols
- Exclude: Ambiguous characters (0, O, l, 1)

**COPY THE GENERATED PASSWORD** and set it as `ADMIN_PASSWORD` in Render.

---

## 📋 STEP 4: RENDER CONFIGURATION CHECKLIST

**In Render Dashboard → EGTI Service → Environment:**

### **Add These 5 Environment Variables:**

| Key | Value | Notes |
|-----|-------|-------|
| `DATABASE_URL` | `jdbc:postgresql://[REDACTED]:5432/egti` | ✅ Existing database |
| `DB_USERNAME` | `[REDACTED]` | ✅ Current username |
| `DB_PASSWORD` | `[REDACTED]` | ✅ Current password |
| `ADMIN_USERNAME` | `bishop` | ✅ Keep or change |
| `ADMIN_PASSWORD` | `[YOUR_GENERATED_PASSWORD]` | ⚠️ **REPLACE WITH STRONG PASSWORD** |

### **Visual Guide:**

```
Render Dashboard:
┌─────────────────────────────────────────────┐
│ Environment Variables                        │
├─────────────────────────────────────────────┤
│ Key: DATABASE_URL                            │
│ Value: jdbc:postgresql://dpg-d61qh...       │
│ [Add] [Cancel]                               │
├─────────────────────────────────────────────┤
│ Key: DB_USERNAME                             │
│ Value: [REDACTED]                             │
│ [Add] [Cancel]                               │
├─────────────────────────────────────────────┤
│ Key: DB_PASSWORD                             │
│ Value: [REDACTED]     │
│ [Add] [Cancel]                               │
├─────────────────────────────────────────────┤
│ Key: ADMIN_USERNAME                          │
│ Value: bishop                                │
│ [Add] [Cancel]                               │
├─────────────────────────────────────────────┤
│ Key: ADMIN_PASSWORD                          │
│ Value: 7K9mP2nQ7vL4jRxwT3yF                 │
│ [Add] [Cancel]                               │
└─────────────────────────────────────────────┘

[Save Changes] ← Click this after adding all 5
```

---

## ✅ STEP 5: VERIFICATION

**After saving environment variables in Render:**

### **1. Check Configuration**

```yaml
□ All 5 environment variables added
□ No typos in variable names (case-sensitive!)
□ DATABASE_URL includes full JDBC URL
□ ADMIN_PASSWORD is strong (20+ characters)
□ Saved changes in Render dashboard
```

### **2. Test Connection (Optional - Advanced)**

**In Render Shell (if available):**

```bash
# Test database connection
echo $DATABASE_URL
echo $DB_USERNAME
# Don't echo password for security

# Verify Spring Boot can read env vars
env | grep -E 'DATABASE|ADMIN'
```

---

## 🚀 STEP 6: DEPLOYMENT SEQUENCE

**After configuring Render environment variables:**

### **1. Update Local application.properties**

**Replace your current `/src/main/resources/application.properties` with:**

```bash
# In your EGTI project directory
cp ~/Downloads/application.properties.SECURED src/main/resources/application.properties
```

**Or manually update lines 7-9:**

```properties
# BEFORE (INSECURE):
spring.datasource.url=jdbc:postgresql://[REDACTED]:5432/egti
spring.datasource.username=[REDACTED]
spring.datasource.password=[REDACTED]

# AFTER (SECURE):
spring.datasource.url=${DATABASE_URL:jdbc:postgresql://localhost:5432/egti_local}
spring.datasource.username=${DB_USERNAME:local_user}
spring.datasource.password=${DB_PASSWORD:local_password}
```

### **2. Add application.properties.example**

```bash
# In your EGTI project directory
cp ~/Downloads/application.properties.example .
git add application.properties.example
```

### **3. Update .gitignore**

**Add to `.gitignore` if not already present:**

```bash
# Application properties with sensitive credentials
src/main/resources/application.properties

# Backup files
*.properties.backup
*.properties.old
```

### **4. Commit and Push**

```bash
# Commit secure configuration
git add application.properties.example .gitignore
git commit -m "security: Migrate credentials to environment variables"

# ⚠️ DO NOT ADD application.properties (it should be in .gitignore)

# Push to GitHub (triggers Render auto-deploy)
git push origin main
```

### **5. Monitor Render Deployment**

```yaml
1. Go to Render Dashboard → EGTI Service → Logs
2. Watch for deployment start: "Deploying..."
3. Wait for Spring Boot startup: "Started EgtiApplication in X seconds"
4. Verify no errors: Check for "ERROR" or "FATAL" in logs
```

---

## âš ï¸ CRITICAL WARNINGS

### **🚨 NEVER COMMIT THESE FILES:**

```bash
# These contain sensitive credentials - MUST be in .gitignore
src/main/resources/application.properties
application.properties.backup
*.properties (if contains real credentials)
```

### **✅ SAFE TO COMMIT:**

```bash
# These are templates/documentation - SAFE for GitHub
application.properties.example
.gitignore
README.md (with setup instructions)
```

---

## 🔍 STEP 7: POST-DEPLOYMENT VERIFICATION

**After Render finishes deploying:**

### **1. Test Site Access**

```bash
# Open in browser
https://egti.onrender.com

# Expected: Homepage loads without errors
```

### **2. Test Login**

```yaml
URL: https://egti.onrender.com/login
Username: bishop (or whatever you set in ADMIN_USERNAME)
Password: [Your generated strong password]

Expected: Successful login → Redirects to dashboard
```

### **3. Check Database Connection**

```yaml
# If members/events are visible → Database connection working ✅
# If error page → Check Render environment variables
```

### **4. Verify No Credentials in Logs**

```bash
# In Render Logs tab, search for:
"password" → Should ONLY see ${DB_PASSWORD} references
"[REDACTED]" → Should NOT appear in logs
"PidDRn" → Should NOT appear anywhere

# If you see actual credentials in logs → STOP and re-check config
```

---

## ðŸ"Š SUCCESS CRITERIA CHECKLIST

```yaml
✅ Render Environment Variables:
   □ DATABASE_URL configured
   □ DB_USERNAME configured
   □ DB_PASSWORD configured
   □ ADMIN_USERNAME configured
   □ ADMIN_PASSWORD configured (strong, 20+ chars)

✅ Local Configuration:
   □ application.properties updated with ${ENV_VAR} syntax
   □ application.properties.example created
   □ .gitignore includes application.properties
   □ No credentials in Git history (if applicable)

✅ Deployment:
   □ Committed changes pushed to GitHub
   □ Render auto-deployment triggered
   □ "Started EgtiApplication" message in logs
   □ No errors during startup

✅ Verification:
   □ Site loads: https://egti.onrender.com
   □ Login works with new admin credentials
   □ Database connection functional
   □ No credentials visible in logs
   □ No credentials in GitHub repository
```

---

## ⚡ EMERGENCY TROUBLESHOOTING

### **If Deployment Fails:**

```yaml
Problem: "Cannot connect to database"
Solution:
  1. Verify DATABASE_URL in Render matches format:
     jdbc:postgresql://HOST:5432/DATABASE
  2. Check DB_USERNAME and DB_PASSWORD are correct
  3. Test database connectivity from Render shell

Problem: "Login doesn't work"
Solution:
  1. Verify ADMIN_USERNAME and ADMIN_PASSWORD in Render
  2. Check password doesn't have special characters causing issues
  3. Try simpler password temporarily for testing

Problem: "Application won't start"
Solution:
  1. Check Render logs for specific error
  2. Verify all env vars are set (5 total)
  3. Ensure no typos in variable names
  4. Rollback to previous Git commit if necessary
```

### **If Credentials Still Exposed:**

```bash
# Check if application.properties is in Git
git ls-files | grep application.properties

# If it shows up → Remove it
git rm --cached src/main/resources/application.properties
git commit -m "security: Remove application.properties from Git"
git push origin main
```

---

## 🏆 VICTORY CONDITIONS

**B-BOMB 03 COMPLETE WHEN:**

```yaml
✅ All 5 environment variables configured in Render
✅ Strong admin password generated and set
✅ Local application.properties uses ${ENV_VAR} syntax
✅ Deployment successful (no errors)
✅ Login functional with new credentials
✅ Zero credentials in Git/logs
✅ Database connection secure and operational
```

---

**🔥 B-BOMB 03 STATUS: READY FOR DETONATION! 🔥**

**Execute this immediately after configuring Render environment variables!**

**TWABAM âš¡! Credentials secured like Fort Knox!** ðŸ'£ðŸ'£ðŸ'£

---

## 📞 QUICK REFERENCE CARD

**Copy this for quick execution:**

```bash
# 1. Render Dashboard
https://dashboard.render.com → EGTI → Environment

# 2. Add 5 Environment Variables (see table above)

# 3. Generate Admin Password
openssl rand -base64 20 | tr -d "=+/" | cut -c1-20

# 4. Update application.properties locally
# (Use application.properties.SECURED template)

# 5. Git commands
git add application.properties.example .gitignore
git commit -m "security: Migrate credentials to environment variables"
git push origin main

# 6. Verify
https://egti.onrender.com/login
```

**TWABAM âš¡! Security migration in 6 simple steps!**
