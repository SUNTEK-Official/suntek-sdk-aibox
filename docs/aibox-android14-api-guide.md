# AI Box - Android 14.0 System API Test Project

## Overview

This is an Android 14.0 (SDK 34) system-level API test and demo project for the **AI Box** device, built on the **SUNTEK** platform. By calling the `ZysjSystemManager` system service provided via `ZY-Interface.jar`, the project enables comprehensive control over device hardware and system functions.

## Project Structure

```
Android14.0.1_mid-new/
├── SUNTEK-Android14.0.1_mid_V1.0.250616.docx   # Project specification document
├── ZY-Interface.jar                             # System API JAR (root-level backup)
├── sample-android/                                  # Android Gradle project
│   ├── app/
│   │   ├── libs/
│   │   │   └── ZY-Interface.jar                 # System service SDK
│   │   └── src/main/java/com/zysj/zysjApi/
│   │       ├── SplashActivity.java              # Splash (permission request → default launcher)
│   │       ├── MainActivity.java                # Main menu (feature entry + watchdog init)
│   │       ├── ShellUtils.java                  # Shell command utility
│   │       ├── ToastUtil.java                   # Toast utility
│   │       └── ...                              # Feature module activities
│   └── settings.gradle                          # Project configuration
└── README.md                                    # This document
```

## Tech Stack

| Item | Version / Description |
|------|-----------------------|
| Android SDK | 34 (Android 14) |
| minSdk | 24 (Android 7.0) |
| Language | Java |
| Build Tool | Gradle |
| UI Framework | AndroidX AppCompat + Material Design |
| System SDK | `ZY-Interface.jar` (`ZysjSystemManager` system service) |
| Repositories | Google / MavenCentral / JitPack |

## Feature Modules

The `MainActivity` serves as the main menu with the following feature entries:

### 1. System UI Control
- **Status Bar Control** (`SystemStatusbarControlActivity`) — Enable/disable status bar and navigation bar
- **Screen Direction Control** (`ScreenDirectionControlActivity`) — Set screen rotation orientation
- **Screen Backlight Control** (`ScreenBacklightControlActivity`) — Adjust screen brightness

### 2. Power & Scheduling
- **Scheduled Power On/Off** (`ScheduledPowerOnAndOffActivity`) — Set scheduled power-on/off dates and times
- **MCU Control** (`McuActivity`) — MCU version query, watchdog status, watchdog feeding

### 3. Network Configuration
- **Ethernet Parameters** (`SettingEthernetParametersActivity`) — Configure IP, gateway, DNS, etc.
- **WiFi Parameters** (`SettingWifiParametersActivity`) — WiFi connection and management

### 4. Hardware Control
- **GPIO Control** (`UserGpioControlActivity`) — Control GPIO 1–8 and 12V LED, set high/low levels
- **MCU Watchdog** — Auto-initialize watchdog (180s timeout), feed every 10s

### 5. System Management
- **Restore Factory Settings** (`RestoreFactorySettingsActivity`) — System data wipe and factory reset
- **Set System Time** (`SetSystemTimeActivity`) — Manual date and time configuration
- **Wireless ADB Debug** (`WirelessAdbDebugControlActivity`) — Enable/disable wireless ADB debugging
- **Device Kernel Info** (`DeviceKernelInfoControlActivity`) — Query device kernel version information
- **Screenshot** (`ScreenShotActivity`) — System screenshot capture

### 6. Others
- **Silent Installation** (`SilentinstallationActivity`) — Background APK installation (commented out)
- **Customization** (`CustomizationActivity`) — Custom feature entry point

### 7. Watchdog

The application automatically initializes the watchdog mechanism on `MainActivity` startup:

- Checks watchdog status; if not enabled, configures **180-second timeout**
- Uses `ScheduledThreadPoolExecutor` to feed the watchdog every **10 seconds**
- Prevents system freeze/hang due to unexpected failures

## Permissions

The application declares the following manifest permissions:
- `ACCESS_NETWORK_STATE` — Network state query
- `MANAGE_EXTERNAL_STORAGE` — Full file access (Android 11+)

`SplashActivity` requests `MANAGE_EXTERNAL_STORAGE` on startup, along with the following runtime permissions:
- `CAMERA`, `WRITE_EXTERNAL_STORAGE`, `READ_EXTERNAL_STORAGE`
- `INTERNET`, `ACCESS_NETWORK_STATE`, `READ_PHONE_STATE`

## System Service

The core system service is provided by `ZysjSystemManager`, registered as the `"zysj"` system service:

```java
ZysjSystemManager manager = (ZysjSystemManager) getSystemService("zysj");
```

Key APIs include:
- `getWatchDogTimeout()` — Get watchdog timeout value
- `getWatchDogStatus()` — Get watchdog on/off status
- `setWatchDogConfigToCh34(int timeout, boolean enable)` — Configure watchdog on CH34
- `feedWatchDog()` — Feed the watchdog
- `zYgetDeviceKernelInfo()` — Get device kernel information
- `setDefaultLauncher(String appId)` — Set the default launcher application

## Boot Flow

```
SplashActivity (HOME/Launcher)
  ├── Request MANAGE_EXTERNAL_STORAGE permission
  ├── Request runtime permissions
  └── Launch MainActivity
        ├── Initialize watchdog
        └── Display feature menu
```

`SplashActivity` is configured as the system default launcher (`HOME` + `DEFAULT` category), so the app launches automatically on device boot.

## Build & Run

```bash
# Enter project directory
cd ZYSJ_API14

# Build APK
./gradlew assembleDebug

# Install on device
./gradlew installDebug
```

### Prerequisites
- Android Studio (latest recommended)
- JDK 8+
- Android SDK 34
- `ZY-Interface.jar` must be placed under `app/libs/`

## Version History

| Version | Date | Description |
|---------|------|-------------|
| V1.0 | 2024-08-10 | Initial release (API documentation date) |
| V1.0.250616 | 2025-06-16 | SUNTEK platform specification version |

## Related Documents

- `SUNTEK-Android14.0.1_mid_V1.0.250616.docx` — Project specification document
- `ZY-Interface.jar` — System API SDK (includes interface documentation)


---

# SUNTEK AI-Box Android 14 API Manual

Android 14.0.1_mid interface
Instructions for use

| Document Number | ZYSJ-API-V1.4.231026 | Security Level | Level I |
| :--- | :--- | :--- | :--- |
| Reviser | Huang Yonglong | Last modified date | 2024.08.19 |
| Reviewer | - | Final approval date | - |
| Approver | - | Final approval date | - |

Revision history

| date | Version | Revision Notes | Reviser |
| :--- | :--- | :--- | :--- |
| 20 22.05.10 | V1.0. 220510 | Initial version | Liang Binbin |
| February 14, 2023 | V1.1. 230214 | Improve the parameter description for timed on/off. | Liang Binbin |
| February 20, 2023 | V1.2. 230220 | Modify the interface description information for the system navigation bar and status bar. | Liang Binbin |
| 2023.03.10 | V1.3. 230310 | Modify the parameters and description of the Ethernet interface. | Liang Binbin |
| October 26, 2023 | V1.3. 231026 | Add screenshot | Huang Yonglong |
| 2024.08.09 | V1.3.240809 | Add microcontroller interface | Huang Yonglong |

Table of contents
Chapter 1: Steps for using interfaces in Android Studio
Chapter 2 Interface Usage Examples and Parameter Details 1
twenty one System Navigation Bar Control 1
twenty two Screen Orientation Control 1
(1) Set screen orientation 1
(2) Obtain screen orientation parameter 1
twenty three Screen backlight control 1
(1) Set screen brightness to 1

## 2.4 Timed On/Off Interface 1

(1) Restart the system 1
(2) Power off 1
(3) Set system time 1
(4) Timed on/off switch 1

## 2.5 System Screenshot Interface 1

(1) System screenshot 1

## 2.6 Restore Factory Settings 1

(1) Restore factory settings 1

## 2.7 User GPIO Interface 1

(1) Set GPIO state 1
(2) Obtain the status of GPIO port 1
(3) Corresponding GPIO pin number 1

## 2.8 Ethernet Parameter Settings 1

(1) Obtain Ethernet IP address 1
(2) Obtain the Ethernet MAC address 1
( 3 ) Obtain Ethernet gateway 1
(4) Obtain the Ethernet subnet mask 1
(5) Obtain Ethernet DNS 1.1
(6) Obtain Ethernet DNS2.1
(7) Obtain Ethernet switch status 1
(8) Control Ethernet switch 1
(9) Set Ethernet to static mode 1
(10) Set Ethernet to dynamic mode 1

## 2.9 WIFI Parameter Settings 1

(1) Obtain the IP address of the Wi-Fi network .
(2) Obtain the MAC address of the Wi-Fi network .
(3) Obtain the Wi-Fi subnet mask 1
(4) Obtain the Wi-Fi gateway 1
(5) Obtain the DNS of the Wi-Fi network .
(6) Obtain the DNS21 of the Wi-Fi network .
(7) Set Wi-Fi to static mode 1
(8) Set Wi-Fi to dynamic mode 1

## 3.0 Wireless ADB Control 1

(1) Enable wireless ADB debugging 1
(2) Disable wireless ADB debugging 1

## 3.1 Obtaining Device Kernel Information 1

(1) Obtain device kernel information 1

## 3.2 Microcontroller 1

(1) Get the watchdog state 1
(2) Set the watchdog timeout period to 1 minute.
(3) Feed the watchdog 1
(4) Obtain MCU version number 1
Chapter Three Frequently Asked Questions Summary 1
Chapter 1: Steps for Using Interfaces in Android Studio

## 1. The integrated development environment used in this article is Android Studio 3.4.1 , as shown in Figure 1-1 below .

Figure 1-1 Android Studio 3.4.1​

## 2. Import the ZY-Interface.jar file into the project . Copy the ZY-Interface.jar file to the libs directory of the project, as shown in Figure 1-2 below.

Figure 1-2 shows the copying of zY-Interface.jar to the libs directory.
Right-click the ZY-Interface.jar file in the libs folder , and then select "Add As Libraryr...". The ZY-Interface.jar file has now been successfully imported, as shown in Figure 1-3 below.
Figure 1-3 Importing the zY-Interface.jar file
Notice:
This API call requires initialization:
Zy sjSystemManager m anager = ( Zy sjSystemManager)getSystemService( " z y sj");
Getting Started with the API
First, you need to declare the object, and then you can start using the API, as shown below.
// Declare the manager object
private ZysjSystemManager manager;
manager = (ZysjSystemManager)getSystemService( " zysj " );
//Using API
manager. zY SystemBar(0);
Chapter Two: Interface Usage Examples and Parameter Details
twenty one System navigation bar control
Navigation bar and status bar : These are the five software buttons at the bottom of the main system interface and the top bar displaying the time and Wi-Fi related graph. The status bar and navigation bar can be hidden via System Settings - Display - Permanently Hide Status Bar, and will not be visible when swiping.
Function of the interface: It can show and hide the navigation bar and status bar . When hidden using this interface, it cannot be shown by sliding. It can only be shown through the interface or system settings.
Application scenarios: Primarily used in projects where end-user operating systems are not permitted or where users cannot exit the currently published system interface;

| Status bar interface (the status bar is located at the top of the system) | - | - |
| :--- | :--- | :--- |
| int zYSystemStatusBar ( int cmd ); | Parameter ( cmd ) | Return value |
| - | Parameters 0, 1, and 2 can be passed in. 1: The status bar cannot be pulled down. 0: The status bar can be pulled down. 2: Get status (returning 1 indicates that the pull-down is hidden and disabled, returning 0 indicates that the pull-down is displayed and enabled) | 0: Success -1: Failure |
| Example: int result = m anager . zYSystemStatusBar ( 0 ); | - | - |


| Navigation bar interface | - | - |
| :--- | :--- | :--- |
| int zYSystemNavigationBar ( int cmd ); | Parameter ( cmd ) | Return value (int) |
| - | Parameters 0, 1, and 2 can be passed in. 1. Gesture navigation is disabled. 0: Gesture navigation is available. 2: Get the status (a return value of 1 indicates that gesture navigation is disabled, and a return value of 0 indicates that gesture navigation is enabled) | 0: Success -1: Failure |
| Example: int result = m anager . zYSystemNavigationBar ( 1 ); | - | - |

twenty two Screen orientation control
Screen display orientation: This refers to the orientation of the system content display. This orientation can be modified in three ways: 1. Through System Settings - Display - Screen Orientation; 2. Automatic rotation via gravity sensor function; 3. Modification via the interface we provide.
Interface function: The interface allows modification of the screen's four orientations (0, 90, 180, 270); and it can also be used to obtain the system's current screen orientation.
Application scenarios: Primarily used in projects where the display angle is not 0 degrees, allowing for modification of the screen orientation;
Set screen orientation
Table 2-2-1 Setting Screen Orientation

| int zYsetScreenDirection (int rotate); | Parameter (rotate) | Return value |
| :--- | :--- | :--- |
| - | 0: Rotate 0 degrees | 0: Setup successful -1: Setup failed |
| - | 1: Rotate 90 degrees | - |
| - | 2: Rotate 180 degrees | - |
| - | 3: Rotate 270 degrees | - |
| Example | int result=manager.zYsetScreenDirection(1); | - |

Get screen orientation parameters
Table 2-2-2 Obtaining Screen Orientation

| i nt zY getScreenDirection(); | Return value |
| :--- | :--- |
| - | 0 : 0 degrees 1 : 90 degrees 2: 180 degrees 3: 270 degrees -1: Failed to retrieve |
| Example | int rotate = manager. zY getScreenDirection(); |

twenty three Screen backlight control
Screen backlight control: This refers to the brightness of the display screen. This can be modified in two ways: 1. Through System Settings - Display - Brightness; 2. Through the interface we provide.
Interface function: The interface allows for control and adjustment of screen brightness in 0 to 2, 4, or 5 levels;
Application scenario: When no one is around, the screen brightness can be appropriately reduced to extend the screen's lifespan.
Set screen brightness
Table 2-3-1 Setting Screen Brightness

| i nt zY setBackLight(int backageLight); | Parameter (cmd) | Return value |
| :--- | :--- | :--- |
| - | Backlight value to be set (0~245) | 0: Setup successful -1 : Setup failed |
| Example | int result = manager. zY setBackLight (144); | - |

( 2 ) Obtain screen brightness
Table 2-3-2 Obtaining Screen Brightness

| int zY getBackLight(); | Return value |
| :--- | :--- |
| - | 0~2 45: Screen backlight -1 : Failed to retrieve |
| Example | int result = manager. zY getBackLight (); |


## 2.4 Timed On/Off Interface

Scheduled power on/off function: This refers to the actual system's scheduled power-on and power-off functions; it can be implemented through System Settings - Accessibility - Scheduled Power On/Off; or through developing a third-party APK using the interface described in this document;
Interface function: Allows you to set the system to start or shut down at a set time.
Application scenarios: Primarily used in unattended high-mounted advertising machine projects;
Restart the system
Table 2-4-1 Restarting the System

| int zY RebootSys(); | Parameter (cmd) | Return value |
| :--- | :--- | :--- |
| - | none | 0: Success |
| Example | int result = manager. zY RebootSys(); | - |

Power off
Table 2-4-2 Power Off

| int zY ShutdownSys(); | Parameter (cmd) | Return value |
| :--- | :--- | :--- |
| - | none | 0: Success |
| Example | int result = manager. zY ShutdownSys (); | - |

Set system time
Table 2-4-3 Setting System Time

| i nt zY set SysTime (int time ); | Parameter ( time ) | Return value |
| :--- | :--- | :--- |
| - | Set the system time (format: year-month-day-hour-minute-second) | 0: Setup successful -1 : Setup failed |
| Example | int result = manager.zY set SysTime( " 2022-05-17-19-14-30 " ) ; | - |

Timed on/off
Table 2-4-4 Timed On/Off

| function | int setTimeToRtc(int cmd , String date , String time); | - | - |
| :--- | :--- | :--- | :--- |
| Parameter (cmd) | Parameter (date) | Parameter (time) | Return value |
| 1: Set to power off 2: Configure startup settings 3: Clear power on/off 4: Clear and shut down 5: Clear boot | Power on/off time | Power on/off date | 0: Setup successful -1: The parameter is not in the range of 1 to 5. -2: The date or time set is empty. -3: The set time is less than the current system time. -4: The time interval between power-off and power-on is less than 3 minutes. |
| Example | int result= manager . setTimeToRtc(1, " 20 21 -6-13 " , " 16:00 " ); | - | - |


## 2.5 System Screenshot Interface

Screenshot function: This refers to capturing a screenshot of the screen. You can display the screenshot button in System Settings - Screenshot Settings and click it to take a screenshot; or you can use the interface described in this article to take a screenshot.
API Function: Enables third-party APKs to implement screenshot functionality;
Application scenario: Primarily used to understand the screen display content of remote settings through screenshots;
Note: If the captured image appears black, you need to confirm whether the current screen is the primary or secondary screen and modify the value of screenId accordingly (e.g., if the current screenId value is 0 and the captured image is black, change the screenId value to 1).
System screenshot
Table 2-5-1 System Screenshots

| String zY ShotScreen (String picPath, String fileName, int screenId); | - |
| :--- | :--- |
| Parameter (cmd) | Return value |
| 1. picPath : Storage path 2. fileName: Screenshot name 3. screenId: Main and secondary screen ID (0 is the main screen ID, 1 is the secondary screen ID) | " 0 " : Screenshot successful " -1 " : picPath is empty " -2 " : fileName is empty " -3 " : screenId is less than 0 " -4 " : File does not exist |
| Example | String result = manager.zY ShotScreen ("/mnt/sdcard/Screenshot/","text.png", 0 ); |


## 2.6 Restore factory settings

Factory reset function: This restores all system settings to their initial state; you can perform a factory reset in System Settings - Backup and Reset; or you can do so through the interface described in this article;
Interface function: It can restore the system to factory settings;
Application scenario: This interface is mainly used when a machine that has been used for a long time malfunctions, so that it can be used to remotely restore factory settings.
Factory reset
Table 2-6-1 Restore Factory Settings

| int zY resetSys(); | Parameter (cmd) | Return value |
| :--- | :--- | :--- |
| - | none | 0: Success |
| Example | int result = manager. zY resetSys (); | - |


## 2.7 User GPIO Interface

Interface function: To control user GPIO ports;
Application scenarios: Primarily used to control or detect the status of external devices;
Set GPIO status
Table 2-7-1 Setting GPIO Status

| i nt set_ zY sj_gpio_value(int which, int value); | Parameter (which) | parameter (value) | Return value |
| :--- | :--- | :--- | :--- |
| - | Indicates the I/O port that needs to be controlled. | 0 : Indicates a low level 1 : Indicates a high level | 0: Setup successful. -1: Setup failed |
| Example | int result = manager.set_zysj_gpio_value(0, 1); // Set I/O port 0 to high level | - | - |

Get the status of GPIO port
Table 2-7-2 Obtaining the Status of GPIO Ports

| int get_ zY sj_gpio_value (int which); | Parameter (which) | Return value |
| :--- | :--- | :--- |
| - | Indicates the I/O port whose status needs to be obtained. | 0: Low level detected. 1: High level obtained -1: Failed to retrieve |
| Example | int result = manager.get_zysj_gpio_value(0); // Get the status of I/O port 0 | - |

Corresponding GPIO pin number
GPIO01 to GPIO06 are numbered 1 to 6 respectively;
The fill light is numbered 24;
Detailed instructions for using the function:
Set the GPIO value:
Power.set_zysj_gpio_value(x,y); (where: x is the GPIO number, corresponding to the number on the board, with a value range of 1, 2, 3, 4, 5, 6; y is the value to set for the GPIO, with a value of 0 for low level and 1 for high level)
Get the GPIO value:
Power.get_zysj_gpio_value(x); (where x is the GPIO number, corresponding to the number on the board, with a value range of 1, 2, 3, 4, 5, 6; return value: 0 indicates low level, 1 indicates high level)
Notice:
Since there is currently no unified GPIO numbering for various boards, if you encounter a board without a standard number, follow the order starting from GND: GND, 04, 03, 02, 01, VCC to correspond to the interface, and only small boards provide 6 GPIOs.

## 2.8 Ethernet Parameter Settings

Ethernet parameter settings interface: This interface allows you to configure the machine's Ethernet static IP address, gateway, and other parameters. There are two ways to modify Ethernet-related parameters: 1. Configure them through System Settings - More - Ethernet Ethernet Mode - Static; 2. Modify them through the interface we provide.
Interface function: The interface allows setting static IP, gateway, DNS and other parameters for Ethernet via a third-party APK;
Application scenario: Remotely modifying relevant parameters of an Ethernet network;
Note: The port next to TF is "eth0", and the second port is "eth1".
Get Ethernet IP address
Table 2-8-1 Obtaining Ethernet IP Address

| String zY getEthIp(String iface); | Parameter (iface) | Return value |
| :--- | :--- | :--- |
| - | i face : Ethernet port name, such as " eth0" or "eth1". | Ethernet IP address For example: "192.0.2.10" |
| Example | String result ; result = manager. zY getEthIp( " eth0 " ); | - |

Get Ethernet MAC address
Table 2-8-2 Obtaining Ethernet MAC Address

| String zY getEthMacAddress ( String iface ); | Parameter ( iface ) | Return value |
| :--- | :--- | :--- |
| - | i face : Ethernet port name, such as " eth0" or "eth1". | Ethernet MAC address For example: 5e:07:77:b7:e5:bf |
| Example | String result = manager. zY getEthMacAddress( " eth0 " ); | - |

( 3 ) Obtain the Ethernet gateway
Table 2-8-3 Obtaining the Ethernet Gateway

| String zY getEthGat ew ay ( String iface ); | Parameter ( iface ) | Return value |
| :--- | :--- | :--- |
| - | i face : Ethernet port name, such as " eth0" or "eth1". | Ethernet gateway For example: "192.0.2.10" |
| Example | String result = manager. zY getEthGate Way ( " eth0 " ); | - |

Get Ethernet subnet mask
Table 2-8-4 Obtaining the Ethernet Subnet Mask

| String zY getEthNetMask ( String iface ); | Parameter ( iface ) | Return value |
| :--- | :--- | :--- |
| - | i face : Ethernet port name, such as " eth0" or "eth1". | Ethernet subnet mask For example: "255.255.255.0" |
| Example | String result = manager. zY getEthNetMask( " eth0 " ); | - |

Get Ethernet DNS1
Table 2-8-5 Obtaining Ethernet DNS1

| String zY getEthDns1 ( String iface ); | Parameter ( iface ) | Return value |
| :--- | :--- | :--- |
| - | i face : Ethernet port name, such as " eth0" or "eth1". | Ethernet DNS1 For example: "192.0.2.10" |
| Example | String result = manager. zY getEthDns1( " eth0 " ); | - |

Get Ethernet DNS2
Table 2-8-6 Obtaining Ethernet DNS2

| String zY getEthDns2 ( String iface ); | Parameter ( iface ) | Return value |
| :--- | :--- | :--- |
| - | i face : Ethernet port name, such as " eth0" or "eth1". | Ethernet DNS2 For example: "0.0.0.0" |
| Example | String result = manager. zY getEthDns2( " eth0 " ); | - |

Get Ethernet switch status
Table 2-8-6 Obtaining Ethernet DNS2

| int zYgetEthernetIfaceState ( String iface ); | Parameter ( iface ) | Return value |
| :--- | :--- | :--- |
| - | i face : Ethernet port name, such as " eth0" or "eth1". | 1: Ethernet is enabled. 0: Ethernet is off. |
| Example | String result = manager. zY getEthDns2( " eth0 " ); | - |

Control Ethernet switch
Table 2-8-6 Obtaining Ethernet DNS2

| boolean zYsetEthernetEnabled ( String iface, boolean enable ); | Parameter ( iface ) | Parameter ( enable ) | Return value |
| :--- | :--- | :--- | :--- |
| - | i face : Ethernet port name, such as " eth0" or "eth1". | true: Enable Ethernet false: Disable Ethernet | true: Success false: failure |
| Example | String result = manager. zYsetEthernetEnabled ( " eth0 ",true ); | - | - |

Set Ethernet to static mode.
Table 2-8-7 Setting Ethernet to Static Mode

| void  zY setEthernetParams (int mode, Map params); | Parameter ( mode ) | Parameters | Return value |
| :--- | :--- | :--- | :--- |
| - | Ethernet mode 0: Dynamic Mode 1: Static Mode | A set consisting of ip, gateway, netmask, dns1, and dns2 IP: IP address gatway: gateway netmask: subnet mask DNS1: DNS1 DNS2: DNS2 Null can be passed if the parameters in the collection are not specified. | none |
| Example | Map<String,String> params = new HashMap<>(); params.put( "ip" ,"192.0.2.10"); params.put( "netmask" ,"255.255.255.0"); params.put( "gateway" ,"192.0.2.10"); params.put( "dns1" , null ); params.put( "dns 2 " ,null); manager. zY setEthernetParams( 1 , params ); | - | - |

Set Ethernet to dynamic mode.
Table 2-8-8 Setting Ethernet to Dynamic Mode

| void zY setEthernetParams (int mode, Map params); | Parameter ( mode ) | Parameters ( params ) | Return value |
| :--- | :--- | :--- | :--- |
| - | Ethernet mode 0: Dynamic Mode 1: Static Mode | A set consisting of ip, gateway, netmask, dns1, and dns2 IP: IP address gatway: gateway netmask: subnet mask dns1: dns1 DNS2: DNS2 Null can be passed if the parameters in the collection are not specified. | none |
| Example | Manager . zY setEthernetParams( 0,null ); | - | - |


## 2.9 WIFI Parameter Settings

WIFI parameter interface: This interface allows you to set the device's WIFI IP address, gateway, and other parameters. There are two ways to modify WIFI-related parameters: 1. Through System Settings - WLAN, adjust the settings in the advanced options that appear when connecting; 2. Modify them through the interface we provide.
Interface function: The interface allows setting parameters such as Wi-Fi IP, gateway, and DNS through a third-party APK;
Application scenario: Remotely modifying Wi-Fi related parameters;
Get the IP address of Wi-Fi
Table 2-9-1 Obtaining the IP Address of Wi-Fi

| String zY getWifiIp(); | parameter | Return value |
| :--- | :--- | :--- |
| - | none | Wi-Fi IP address |
| Example | String result = manager. zY getWifiIp (); | - |

Get the MAC address of Wi-Fi
Table 2-9-2 Obtaining the MAC Address of Wi-Fi

| String zY getWifiMacAddress(); | parameter | Return value |
| :--- | :--- | :--- |
| - | none | Wi-Fi MAC address |
| Example | String result = manager. zY getWifiMacAddress(); | - |

Get the Wi-Fi subnet mask
Table 2-9-3 Obtaining the Wi-Fi subnet mask

| String zY getWifiNetMask(); | Parameter (cmd) | Return value |
| :--- | :--- | :--- |
| - | none | Wi-Fi subnet mask |
| Example | String result = manager.zY getWifiNetMask (); | - |

Get the Wi-Fi gateway
Table 2-9-4 Obtaining the Wi-Fi Gateway

| String zY getWifiGatWay(); | Parameter (cmd) | Return value |
| :--- | :--- | :--- |
| - | none | Wi-Fi gateway |
| Example | String result = manager. zY getWifiGatWay(); | - |

Get the DNS1 of Wi-Fi
Table 2-9-5 Obtaining WiFi DNS1

| String zY getWifiDns1(); | Parameter (cmd) | Return value |
| :--- | :--- | :--- |
| - | none | Wifi DNS1 |
| Example | String result = manager. zY getWifiDns1(); | - |

Get the DNS2 of Wi-Fi
Table 2-9-6 Obtaining DNS2 for Wi-Fi

| String zY getWifiDns2(); | Parameter (cmd) | Return value |
| :--- | :--- | :--- |
| - | none | Wifi DNS2 |
| Example | String result = manager.zY getWifiDns2 (); | - |

Set Wi-Fi to static mode
Table 2-9-7 Setting Wi-Fi to Static Mode

| void zY setWifiParams(int mode,Map params) | Parameter (mode) | Parameters | Return value |
| :--- | :--- | :--- | :--- |
| - | WIFI mode 0: Dynamic Mode 1: Static Mode | A set consisting of ip, gateway, netmask, dns1, and dns2 IP: IP address gatway: gateway netmask: subnet mask DNS1: DNS1 DNS2: DNS2 Null can be passed if the parameters in the collection are not specified. | none |
| Example | Map<String,String> params = new HashMap<>(); params.put( zY sjSystemManager.STATIC_IP,"192.0.2.10"); params.put( zY sjSystemManager.STATIC_NETMASK,"192.0.2.10"); params.put( zY sjSystemManager.STATIC_GATEWAY,"255.255.255.0"); params.put( zY sjSystemManager.STATIC_DNS1,"8.8.8.8"); params.put( zY sjSystemManager.STATIC_DNS2,"8.8.4.4");  manager.zY setWifiParams( 1 ,params ); | - | - |

Set Wi-Fi to dynamic mode.
Table 2-9-8 Setting Wi-Fi to Dynamic Mode

| void zY setWifiParams(int mode,Map params) | Parameter (mode) | Parameters | Return value |
| :--- | :--- | :--- | :--- |
| - | WIFI mode 0: Dynamic Mode 1: Static Mode | A set consisting of ip, gateway, netmask, dns1, and dns2 IP: IP address gatway: gateway netmask: subnet mask DNS1: DNS1 DNS2: DNS2 Null can be passed if the parameters in the collection are not specified. | none |
| Example | manager.zY setWifiParams( 0 ,null ); | - | - |


## 3.0 Wireless ADB Control

Wireless ADB debugging: This refers to software debugging without requiring a data cable connection between the device and the computer. There are two ways to enable and disable it: 1. Enable or disable it through System Settings - Accessibility - Wireless ADB Debugging; 2. Through the wireless ADB debugging interface described in this article.
Interface function: This interface allows you to enable or disable the wireless ADB function.
Application scenarios: Primarily used when it is inconvenient to connect the device to the computer using a data cable;
Enable wireless ADB debugging
Table 3-0-1 Enable Wireless ADB Debugging

| void zY setAdbWiress(boolean enable) | Parameter (cmd) | Return value |
| :--- | :--- | :--- |
| - | false: Close true: Open | none |
| Example | m zY sjSystemManager. zY setAdbWiress ( true ); | - |

Disable wireless ADB debugging
Table 3-0-2 Disabling Wireless ADB Debugging

| void zY setAdbWiress(boolean enable) | Parameter (enable) | Return value |
| :--- | :--- | :--- |
| - | false: Close true: Open | none |
| Example | m anager.zY setAdbWiress ( false ); | - |


## 3.1 Obtaining Device Kernel Information

Device kernel information parameter interface: This interface allows you to obtain relevant information about the device kernel ; Interface function: This interface enables you to obtain relevant information about the device kernel through a third-party APK ;
Application scenario: Obtaining information related to the device kernel ;
Obtain device kernel information
Table 3-1-1 Obtaining Device Kernel Information

| int zY getDeviceKernelInfo () | Parameter (cmd) | Return value |
| :--- | :--- | :--- |
| - | none | Device kernel related information |
| Example | String DeviceKernelInfo = m anager . zY getDeviceKernelInfo (); | - |


## 3.2 Microcontroller

Get watchdog status
Table 3-2-1 Obtaining Watchdog Status

| int getWatchDogStatus() | Parameter (cmd) | Return value |
| :--- | :--- | :--- |
| - | none | int: 0: Off, 1: Enable |
| Example | int status = m anager . getWatchDogStatus (); | - |

Set watchdog timeout
Table 3-2-2 Setting Watchdog Timeout

| int setWatchDogConfigToCh34(int time, boolean enable) | Parameters ( time, enable ) | Return value |
| :--- | :--- | :--- |
| - | time: 5-180 seconds, enable: false: off, true: enabled | int: 0 for success, 1 for failure |
| Example | int status = m anager . setWatchDogConfigToCh34 ( 8, true ); | - |

Feed the watchdog
Table 3-2-3 Feeding the guard dog

| int feedWatchDog() | Parameter ( cmd ) | Return value |
| :--- | :--- | :--- |
| - | none | int: 0 for success, 1 for failure |
| Example | int status = m anager . feedWatchDog (); | - |

Get MCU version number
Table 3-2-4 Setting Watchdog Timeout

| int getMcuVersion() | Parameter ( cmd ) | Return value |
| :--- | :--- | :--- |
| - | none | int: version number |
| Example | int version = m anager . getMcuVersion (); | - |

Chapter Three Frequently Asked Questions Summary
FAQ1: No issues have been identified yet.
