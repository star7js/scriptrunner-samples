## ScriptRunner Samples

This repository contains sample script plugins for ScriptRunner for various Atlassian products.

## Original Project Source

This project is based on the original `scriptrunner-samples` provided by Adaptavist Labs:
<https://bitbucket.org/adaptavistlabs/scriptrunner-samples>

This version contains modifications, primarily to ensure compatibility with Java 17 and fix various build/runtime issues encountered with recent Atlassian Plugin SDK versions.

## Project Setup and Java 17 Compatibility

These sample projects, originally designed for older environments, required several updates to build and run successfully using the Atlassian Plugin SDK with a **Java 17 JDK**.

### Key Changes Made:

1.  **Java Version Configuration (Both Jira & Confluence):**
    *   Added the `maven-compiler-plugin` to both `jira/pom.xml` and `confluence/pom.xml` to explicitly set the Java source and target compatibility versions to `17`. This ensures the code is compiled correctly for Java 17.

2.  **JVM Arguments for Java 17+ (Both Jira & Confluence):**
    *   Added several `--add-opens` flags to the `<jvmArgs>` section within the `jira-maven-plugin` and `confluence-maven-plugin` configurations.
    *   **Reason:** Java 9+ introduced the Java Platform Module System (JPMS), which restricts reflective access to internal JDK classes by default. Atlassian products (like Jira 9.x and Confluence 8.x) and their dependencies still rely on this reflection. These flags explicitly grant the necessary permissions to avoid `InaccessibleObjectException` or `IllegalAccessError` runtime errors when running `atlas-debug` or similar commands.
    *   *Note:* The specific flags added were based on errors encountered during startup. The set added to Confluence was copied from Jira and might contain more flags than strictly necessary; a TODO comment was left in `confluence/pom.xml`.

3.  **Jira POM (`jira/pom.xml`) Specific Fixes:**
    *   **Commented out `<pluginArtifacts>`:** This section, previously needed for older AMPS versions (related to AMPS-1404), appeared to interfere with dependency resolution for `atlassian-jira-platform` and other core artifacts in the current setup. Removing it allowed Maven to resolve dependencies correctly using the standard `<repositories>` and `<pluginRepositories>`.
    *   **Commented out `<applications>`:** The `jira-software` and `jira-servicedesk` application definitions caused build failures because their version properties (`${jira.software.application.version}`, etc.) were not defined. These applications are often not required for developing core plugin functionality, so they were commented out.
    *   **Added Explicit Repositories:** Explicitly added the `atlassian-public` repository to both `<repositories>` and `<pluginRepositories>` sections to assist Maven, although the primary fix for the initial dependency errors was removing `<pluginArtifacts>`.

4.  **Confluence POM (`confluence/pom.xml`) Specific Fixes:**
    *   **Changed Parent POM Version:** The parent POM version for `scriptrunner-confluence-standard` was changed from `78` to `71`.
    *   **Reason:** Version `78` consistently failed to resolve, even when pointing directly at the Adaptavist repository (`nexus.adaptavist.net`). This strongly suggests version `78` is not available in the *public* external repository. Version `71` was confirmed to be available and resolved successfully.
    *   **Added Explicit Repositories:** Added `adaptavist-external` and `atlassian-public` repositories to both `<repositories>` and `<pluginRepositories>` to ensure Maven checked all necessary locations for the parent POM and other dependencies.

### Summary for Users:

*   You **must** use a **Java 17 JDK** to build and run these projects.
*   The included POM files are configured with the necessary compiler settings and JVM arguments for Java 17 compatibility.

### Running the Applications:

To run the applications with the sample plugin installed, navigate to the **project root directory** in your terminal and use the following commands:

**To run Jira:**

```bash
cd jira && atlas-mvn jira:debug -U
```

**To run Confluence:**

```bash
cd confluence && atlas-mvn confluence:debug -U
```

*   These commands will download dependencies, build the plugin, start the application (Jira or Confluence), and install the plugin.
*   The application will typically be available at `http://localhost:2990/jira` for Jira or `http://localhost:8080/confluence` for Confluence (check the console output for the exact URL and port).
*   Press `Ctrl+C` in the terminal where the command is running to stop the application.
