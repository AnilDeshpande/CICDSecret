# CI/CD Secret Management Demo — Summary

---

## 🎬 Video 1 — *Securing Secrets in an Android Project (Local)*
**Branch:** `video-one` · **Goal:** Show the right way to keep API keys out of source control.

| Step | Commit | What Was Done |
|------|--------|---------------|
| **A** | Baseline UI | Scaffold a Compose screen that displays `APPLICATION_ID` and a secret-status label. No secret logic yet. |
| **B** | Hardcoded key ❌ | Embed `DEMO_API_KEY` directly in Kotlin source — the **wrong** way — to show why this is dangerous. |
| **C+D** | `local.properties` + Gradle | Move the key into `local.properties` (git-ignored). Gradle reads it via `Properties`, injects it as a `buildConfigField`. One combined commit because one without the other would break the build. |
| **E** | UI reads `BuildConfig` | The Compose UI now reads `BuildConfig.DEMO_API_KEY` and displays a masked version (e.g. `abc•••xyz`). |
| **F** | Fail-fast demo | Temporarily remove the key from `local.properties` → build fails with a clear error. Re-add it so the final commit is buildable. |

**Key Takeaway:** Secrets live in `local.properties` (never committed), Gradle injects them at build time, and a fail-fast `error()` catches missing keys immediately.

---

## 🎬 Video 2 — *Running the Build on GitHub Actions CI*
**Branch:** `video-two` (builds on top of video-one) · **Goal:** Set up CI and securely pass the same secret to the cloud build.

| Step | Commit | What Was Done |
|------|--------|---------------|
| **B** | Workflow skeleton | Created `.github/workflows/android-ci.yml` with `workflow_dispatch` trigger (manual run with branch dropdown). |
| **C+D** | Checkout + JDK | Added `actions/checkout@v5` and `actions/setup-java@v5` (Temurin JDK 21). |
| **E** | Gradle build | Added `./gradlew assembleDebug` step. |
| **F** | Upload artifact | Added `actions/upload-artifact@v5` — the debug APK is downloadable from the Actions run page. |
| **G** | Demonstrate failure 🔴 | Triggered CI — build fails because `local.properties` doesn't exist on the runner, proving the secret is missing. |
| **H** | Add GitHub Secret | Manually added `DEMO_API_KEY` in **Settings → Secrets → Actions**. |
| **I+J** | Inject secret + env fallback | Workflow passes `${{ secrets.DEMO_API_KEY }}` as an env var. `build.gradle.kts` now uses a **two-tier lookup**: `System.getenv()` (CI) → `local.properties` (local) → fail-fast error. One combined commit. |
| **K** | Verify green build 🟢 | Triggered CI again — build succeeds, APK artifact is produced and downloadable. |

**Key Takeaway:** The same `build.gradle.kts` works both locally and on CI. Secrets are stored in GitHub Actions Secrets and injected as environment variables — never committed to the repo.

---

### Files Modified Across Both Videos

| File | Purpose |
|------|---------|
| `app/build.gradle.kts` | Loads secret from env var → `local.properties` → fail-fast error; injects as `buildConfigField` |
| `local.properties` | Holds `DEMO_API_KEY=...` locally (git-ignored) |
| `app/src/main/.../MainActivity.kt` | Compose UI displaying masked secret from `BuildConfig` |
| `.github/workflows/android-ci.yml` | CI workflow: checkout → JDK → build → upload artifact, with secret injection |
| `.gitignore` | Ensures `local.properties` is never committed |

