# Shahid TV Browser V4 – Desktop UA Compatibility Test

This version is based on V3 and changes only the browser User-Agent to a desktop Chrome-style identity.

Reason: the TV's built-in Chrome renders Shahid's web interface correctly, but the old Android/WebView flow asks to install the Shahid app. V4 tests whether Shahid serves a playable web flow to a desktop browser identity.

**Important:** this is a compatibility experiment only. It does not implement or bypass DRM/content protection. Playback can still be unavailable if Shahid requires an authorised device or supported DRM.

Build with the included GitHub Actions workflow.
