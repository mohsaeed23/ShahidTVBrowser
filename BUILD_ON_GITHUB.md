# استخراج APK بدون Android Studio

## الطريقة
1. اعمل حساب مجاني على GitHub.
2. أنشئ Repository جديد، ويفضل أن تسميه:
   `ShahidTVBrowser`
3. ارفع **كل ملفات هذا المشروع** إلى الـRepository.
4. افتح تبويب **Actions**.
5. اختر workflow باسم:
   **Build Shahid TV Browser APK**
6. اضغط **Run workflow**.
7. انتظر انتهاء البناء.
8. افتح آخر تشغيل ناجح، ثم من قسم **Artifacts** حمّل:
   `ShahidTVBrowser-debug-apk`
9. فك ضغط الـArtifact، وستجد:
   `app-debug.apk`
10. انقل الـAPK إلى التلفزيون وثبته.

## ملاحظات
- المشروع يستهدف Android 4.4.4 (API 19).
- عملية البناء تتم على GitHub Actions ولا تحتاج Android Studio على جهازك.
- هذا التطبيق لا يتجاوز DRM أو حماية شاهد؛ الهدف اختبار توافق المتصفح وتشغيل HTML5 عندما يسمح الجهاز والموقع بذلك.
