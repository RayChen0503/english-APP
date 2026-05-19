# Sora Companion

偏鄉學生雙軌學習平台 Android 原型。第一版聚焦「情緒斷點」：學生卡關時先由 AI 接住，再把整理好的斷點摘要交給雲端志工或老師。

## 第一版功能

- 學生入口：心情檢測、今日 3-5 分鐘任務、錯題回饋、修復任務、學習地圖。
- 志工/老師入口：學生狀態、卡關斷點、AI 已嘗試提示、建議陪伴語。
- 本機假資料：先展示完整流程，不串接後端或真 AI。
- 原生 Android View：避免第一版依賴大量外部 UI 套件。

## 專案結構

```text
SoraCompanion/
  app/
    src/main/
      java/tw/edu/citizenaction/soracompanion/MainActivity.kt
      res/
  build.gradle.kts
  settings.gradle.kts
```

## 開啟方式

1. 用 Android Studio 開啟 `android/SoraCompanion`。
2. 等待 Gradle 同步。
3. 選擇模擬器或 Android 手機執行 `app`。

目前這台環境沒有偵測到 Android SDK、Gradle 或 Git，所以我先建立可由 Android Studio 開啟的專案原始碼。若要在本機直接產出 APK，需要安裝 Android Studio/SDK 或提供可用 Gradle wrapper。

## 下一版建議

- 將學生、任務、錯題斷點抽成 Repository。
- 加入 Room 或 Firebase 儲存學習紀錄。
- 串接 OpenAI API 或校內可用模型做 AI 提示。
- 補教師端班級列表與學生詳細紀錄。
- 補 UI 測試與可匯出的展示 APK。
