# Shahid Desktop Browser - Android 4.4

تم تحويل ملف HTML المرفوع إلى مشروع Android متوافق مع API 19.

النقاط الأساسية:
- minSdkVersion 19 (Android 4.4.4)
- Desktop Chrome User-Agent مضبوط من طبقة Android نفسها.
- JavaScript / DOM Storage / cookies / HTML5 media مفعلة.
- واجهة HTML الأصلية موجودة داخل assets.
- GitHub Actions جاهز للبناء باستخدام نفس بيئة البناء التي نجحت سابقاً.

مهم: هذا يجعل الطلبات الصادرة من WebView تستخدم User-Agent لسطح مكتب، لكنه لا يتجاوز DRM أو حماية المحتوى ولا ينشئ CDM غير موجود في الجهاز.
