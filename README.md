# Sentinel Admin (Android)

**Sentinel Admin** is the native Android control center for the Sentinel AI Safety Platform. It provides the "Human-in-the-Loop" governance layer, allowing administrators to monitor autonomous agents, view real-time execution logs, and authorize high-risk actions from anywhere.

Most importantly, it houses the **Biometric Kill Switch**—a hardware-secured safety mechanism to instantly neutralize rogue agents.

## Key Features

* **🛡️ Biometric Kill Switch:** Leverages the device's hardware security (Fingerprint/FaceID) to authorize the termination of a rogue agent. One tap + One scan = Total Network Lockdown.
* **💻 The "Matrix" Terminal:** Watch your AI Agents "think" in real-time. Streams live execution logs from the Python Bridge with a hacker-style green-on-black UI.
* **🔔 Permission Dashboard:** Receive alerts when an agent requests access to production (e.g., "Deploy to Prod"). Approve or Deny with a single tap.
* **Fleet Overview:** Monitor the health and status of all active AI agents in your infrastructure.

## Tech Stack

* **Language:** Kotlin
* **UI Framework:** Jetpack Compose (Modern Declarative UI)
* **Architecture:** MVVM (Model-View-ViewModel)
* **Networking:** Retrofit + OkHttp
* **Concurrency:** Kotlin Coroutines & StateFlow
* **Security:** Android Biometric API

##  Screenshots

*(Add screenshots here of the Dashboard, the Log Terminal, and the Biometric Prompt)*

## Installation & Setup

1.  **Clone the Repository:**
    ```bash
    git clone https://github.com/Azam0221/Sentinel_App_Android.git
    ```

2.  **Open in Android Studio:**
    * File -> Open -> Select the cloned folder.
    * Let Gradle sync.

3.  **🔧 CRITICAL CONFIGURATION (Network):**
    Since your Spring Boot backend is likely running on your laptop (localhost), you must tell the Android Emulator (or physical device) where to find it.

    * Open `com/example/sentinel/network/RetrofitClient.kt`.
    * Update the `BASE_URL`:
        * **If using Android Emulator:** Use `http://10.0.2.2:8080/api/v1/`
        * **If using Physical Device:** Use your laptop's local Wi-Fi IP (e.g., `http://192.168.1.5:8080/api/v1/`).

4.  **Run the App:**
    Select your device/emulator and click **Run**.

## Usage Workflow

1.  **Start the Fleet:** Ensure your **Spring Boot Backend** and **Python Bridge** are running on your laptop.
2.  **Monitor:** Open the app to see the list of connected agents (e.g., "DevOps Commander").
3.  **View Logs:** Tap the **Logs** button to enter the "Terminal View." Watch as the agent executes tasks in Archestra.
4.  **Authorize:** When the Agent asks for permission (Yellow Alert), go to the **Alerts** tab and tap **Approve**.
5.  **Emergency Stop:** If the logs look suspicious, tap **Kill Agent**. Scan your fingerprint. The agent is instantly disconnected.

## Troubleshooting

* **"Connection Refused":** This is almost always an IP address issue.
    * Make sure your phone and laptop are on the **same Wi-Fi**.
    * Make sure your laptop's firewall isn't blocking port `8080`.
* **No Logs Appearing:** Ensure the Python Bridge is running and properly connected to Archestra.

## Future Roadmap

* **Push Notifications:** Replace polling with Firebase Cloud Messaging (FCM) for instant alerts.
* **Wear OS Support:** Approve deployments from your smartwatch.
* **Multi-Admin Voting:** Require approval from 2 different admins for critical infrastructure changes.
