# Shahid TV Browser V5 – Desktop Identity Compatibility Test

This version keeps the Android WebView engine, but presents a desktop Chrome identity at two layers:

1. HTTP User-Agent: Windows 10 + Chrome 120.
2. JavaScript navigator properties: platform=Win32, vendor=Google Inc., maxTouchPoints=0, desktop userAgent/appVersion, and a minimal window.chrome object.

The purpose is to test ordinary website/browser compatibility on an Android 4.4.4 TV. It does not alter DRM, licenses, or content-protection mechanisms.

Build using the included GitHub Actions workflow.
