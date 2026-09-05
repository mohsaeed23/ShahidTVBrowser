# Shahid TV Browser

نسخة تجريبية مخصصة لأجهزة Android TV القديمة (API 19 / Android 4.4.4).

## الوظائف
- يفتح Shahid داخل WebView.
- JavaScript / DOM storage / cookies مفعلة.
- Full-screen HTML5 video عبر WebChromeClient.
- واجهة Landscape مناسبة للتلفزيون والريموت.
- زر Back يرجع داخل صفحات الموقع.
- ضغط زر Menu في الريموت يعرض معلومات تشخيصية عن WebView والجهاز.
- لا يتجاوز DRM أو حماية المحتوى.

## البناء
المشروع يستخدم Android Gradle Plugin 3.5.4 وcompileSdk 28، مع min/target SDK 19.

افتح المجلد في Android Studio متوافق مع المشروع ثم Build > Build APK(s).

## ملاحظة مهمة
هذا التطبيق لا يضيف DRM جديدًا للجهاز. إذا كان جهاز Android 4.4.4 يفتقد مستوى Widevine أو متطلبات Shahid الحديثة، فلن يستطيع التطبيق تجاوز ذلك. الهدف من النسخة الأولى تحديد هل المشكلة من المتصفح/WebView أم من DRM/النظام.


## بناء APK من GitHub بدون Android Studio
راجع `BUILD_ON_GITHUB.md`. يوجد Workflow جاهز داخل `.github/workflows/build-apk.yml` يقوم ببناء APK ورفعه كـArtifact.
