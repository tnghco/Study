# StudyGate 30초 테스트 버전

갤러리에 있는 강의 영상을 StudyGate 앱 안에서 30초 이상 보면, 그날 YouTube 앱 차단이 풀리는 Android 앱입니다.

## 모바일만으로 APK 만들기: GitHub Actions 사용

Android Studio가 없어도 GitHub가 대신 APK를 빌드하게 할 수 있습니다.

1. 휴대폰에서 GitHub 앱 또는 github.com에 로그인합니다.
2. 새 저장소를 만듭니다. 예: `StudyGate`
3. 이 ZIP 파일의 압축을 풉니다.
4. 압축 안의 파일과 폴더 전체를 저장소에 업로드합니다.
   - `app/`
   - `.github/workflows/build-apk.yml`
   - `build.gradle.kts`
   - `settings.gradle.kts`
   - `gradle.properties`
5. 저장소의 **Actions** 탭으로 갑니다.
6. **Build StudyGate APK**를 선택합니다.
7. **Run workflow**를 누릅니다.
8. 빌드가 끝나면 실행 결과 페이지 아래쪽의 **Artifacts**에서 `StudyGate-30sec-debug-apk`를 다운로드합니다.
9. 압축을 풀면 `app-debug.apk`가 있습니다.
10. 휴대폰에서 APK를 열어 설치합니다.

## 앱 사용법

1. StudyGate 앱을 엽니다.
2. **강의 영상 선택**을 누르고 갤러리의 강의 영상을 선택합니다.
3. **YouTube 차단 권한 켜기**를 누릅니다.
4. Android 접근성 설정에서 StudyGate 서비스를 켭니다.
5. 앱 안에서 강의 영상을 30초 이상 재생합니다.
6. 이후 YouTube 앱 실행이 허용됩니다.

## 주의

- 이 테스트 버전의 기준 시간은 30초입니다.
- 공식 YouTube 앱 패키지 `com.google.android.youtube`만 차단합니다.
- Chrome, Samsung Internet 같은 브라우저에서 여는 YouTube는 아직 차단하지 않습니다.
- 접근성 권한을 끄면 차단이 풀립니다. 자기통제용 앱에 가깝습니다.
