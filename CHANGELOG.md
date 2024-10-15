# Changelog

All notable changes to this project will be documented in this file.

The format is a modified version of [Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).
- `Added` - for new features.
- `Changed ` - for changes in existing functionality.
- `Improved` - for enhancement or optimization in existing functionality.
- `Removed` - for now removed features.
- `Fixed` - for any bug fixes.
- `Other` - for technical stuff.

## [Unreleased]
### Added
- Option to disable reader zoom out ([@Splintorien](https://github.com/Splintorien)) ([#302](https://github.com/mihonapp/mihon/pull/302))
- Source name and tracker urls to app generated `ComicInfo.xml` file ([@Shamicen](https://github.com/Shamicen)) ([#459](https://github.com/mihonapp/mihon/pull/459))
- Option to migrate in Duplicate entry dialog ([@sirlag](https://github.com/sirlag)) ([#492](https://github.com/mihonapp/mihon/pull/492))

- Upcoming screen to visualize expected update dates ([@sirlag](https://github.com/sirlag)) ([#420](https://github.com/mihonapp/mihon/pull/420))
- Crash screen error message to the top of the crash log generated from that screen ([@FooIbar](https://github.com/FooIbar)) ([#742](https://github.com/mihonapp/mihon/pull/742))
- Support for 7Zip and RAR5 archives ([@FooIbar](https://github.com/FooIbar), [@null2264](https://github.com/null2264)) ([#949](https://github.com/mihonapp/mihon/pull/949), [#967](https://github.com/mihonapp/mihon/pull/967))
- Extra configuration options to e-ink page flashes ([@sirlag](https://github.com/sirlag)) ([#625](https://github.com/mihonapp/mihon/pull/625))
- 8-bit+ AVIF image support ([@WerctFourth](https://github.com/WerctFourth)) ([#971](https://github.com/mihonapp/mihon/pull/971))
- Smart update dialog message when no predicted released date exists ([@Animeboynz](https://github.com/Animeboynz)) ([#977](https://github.com/mihonapp/mihon/pull/977))
- Save global search "Has result" choice ([@AntsyLich](https://github.com/AntsyLich)) ([`5a61ca5`](https://github.com/mihonapp/mihon/commit/5a61ca5535fe0d9e8e7bcb9e665ba2f9cb0cf649))
- Option to copy reader panel to clipboard ([@Animeboynz](https://github.com/Animeboynz)) ([#1003](https://github.com/mihonapp/mihon/pull/1003))
- Copy Tracker URL option to tracker sheet ([@mm12](https://github.com/mm12)) ([#1101](https://github.com/mihonapp/mihon/pull/1101))
- A button to exclude all scanlators in exclude scanlators dialog ([@AntsyLich](https://github.com/AntsyLich)) ([`84b2164`](https://github.com/mihonapp/mihon/commit/84b2164787a795f3fd757c325cbfb6ef660ac3a3))
- Open in browser option to reader menu ([@mm12](https://github.com/mm12)) ([#1110](https://github.com/mihonapp/mihon/pull/1110))
- Option to skip downloading duplicate read chapters ([@shabnix](https://github.com/shabnix)) ([#1125](https://github.com/mihonapp/mihon/pull/1125))

### Changed
- Read archive files from memory instead of temporarily extracting to internal storage ([@FooIbar](https://github.com/FooIbar)) ([#326](https://github.com/mihonapp/mihon/pull/326))
  - Fix dual page split ([@FooIbar](https://github.com/FooIbar)) ([#485](https://github.com/mihonapp/mihon/pull/485))
- Bump default user agent ([@AntsyLich](https://github.com/AntsyLich)) ([`8160b47`](https://github.com/mihonapp/mihon/commit/8160b47ff5fbbd9b32caeb462b5be881fabd3449))
- Wait for sources to be initialized before performing source related tasks ([@jobobby04](https://github.com/jobobby04)) ([`a08e03f`](https://github.com/mihonapp/mihon/commit/a08e03f5cbf3f4e6be1de35f97ef8ebb26a1210e))
- Duplicate entry dialog UI ([@sirlag](https://github.com/sirlag)) ([#492](https://github.com/mihonapp/mihon/pull/492))
- Extension trust system
  - Store extension repo details from `repo.json` in database ([@sirlag](https://github.com/sirlag)) ([#506](https://github.com/mihonapp/mihon/pull/506))
    - Fix extension repo migration not triggering ([@AntsyLich](https://github.com/AntsyLich)) ([`9672ea8`](https://github.com/mihonapp/mihon/commit/9672ea8b1b06f464800e310c96e060ead182f7ca))
    - Refactor the ExtensionRepoService to use DTOs ([@MajorTanya](https://github.com/MajorTanya)) ([#573](https://github.com/mihonapp/mihon/pull/573))
    - Fix extension repo name is used to construct URL instead of baseUrl ([@MajorTanya](https://github.com/MajorTanya)) ([#572](https://github.com/mihonapp/mihon/pull/572))
    - Fix crash with `TypeReference` issue when creating extension repo ([@AntsyLich](https://github.com/AntsyLich)) ([#574](https://github.com/mihonapp/mihon/pull/574), [`e020ae5`](https://github.com/mihonapp/mihon/commit/e020ae5ed558e80742ef0ad8bfa0f69af0959d5a))
      - Fix mishap in [`e020ae5`](https://github.com/mihonapp/mihon/commit/e020ae5ed558e80742ef0ad8bfa0f69af0959d5a) ([@AntsyLich](https://github.com/AntsyLich)) ([`6965e59`](https://github.com/mihonapp/mihon/commit/6965e59a643c67a2bf81b3c69ec70268e5da5797))

([@AntsyLich](https://github.com/AntsyLich), [@Animeboynz](https://github.com/Animeboynz) ([#570](https://github.com/mihonapp/mihon/pull/570), [#1057](https://github.com/mihonapp/mihon/pull/1057))

- Make category backup/restore not dependant on library backup ([@AntsyLich](https://github.com/AntsyLich)) ([`56fb4f6`](https://github.com/mihonapp/mihon/commit/56fb4f62a152e87a71892aa68c78cac51a2c8596))
- Kitsu domain to `kitsu.app` ([@MajorTanya](https://github.com/MajorTanya)) ([#1106](https://github.com/mihonapp/mihon/pull/1106))
- Respect privacy settings in extension update notification ([@Animeboynz](https://github.com/Animeboynz)) ([#1156](https://github.com/mihonapp/mihon/pull/1156))

### Improved

- Long strip reader performance ([@FooIbar](https://github.com/FooIbar), [@wwww-wwww](https://github.com/wwww-wwww)) ([#687](https://github.com/mihonapp/mihon/pull/687))
- Performance when looking up specific files ([@raxod502](https://github.com/raxod502)) ([#728](https://github.com/mihonapp/mihon/pull/728))
- Chapter number parsing ([@Naputt1](https://github.com/Naputt1)) ([`6a80305`](https://github.com/mihonapp/mihon/commit/6a80305d6c572da6c08c0c69f5c25ff26ecf7383))
- Error message on restoring if backup decoding fails ([@vetleledaal](https://github.com/vetleledaal)) ([#1056](https://github.com/mihonapp/mihon/pull/1056))

### Fixed
- Extracting `ComicInfo.xml` from local source archives ([@FooIbar](https://github.com/FooIbar)) ([#325](https://github.com/mihonapp/mihon/pull/325))
- Chapter download indicator ([@ivaniskandar](https://github.com/ivaniskandar)) ([`d8b9a9f`](https://github.com/mihonapp/mihon/commit/d8b9a9f593911569ff2bceb49b4f020978d0d2e1))
- Issues with shizuku in a multi user setup ([@Redjard](https://github.com/Redjard)) ([#494](https://github.com/mihonapp/mihon/pull/494))
- Fix reader page image not being decoded until it's visible ([@FooIbar](https://github.com/FooIbar)) ([#563](https://github.com/mihonapp/mihon/pull/563))

- Extension being marked as not installed instead of untrusted after updating with private installer ([@AntsyLich](https://github.com/AntsyLich)) ([`2114514`](https://github.com/mihonapp/mihon/commit/21145144cdf550aa775047603e06e261951ebc42))
- Extension update counter not updating due to extension being marked as untrusted ([@AntsyLich](https://github.com/AntsyLich)) ([`2114514`](https://github.com/mihonapp/mihon/commit/21145144cdf550aa775047603e06e261951ebc42))
- `Key "extension-XXX-YYY" was already used` crash ([@AntsyLich](https://github.com/AntsyLich)) ([`2114514`](https://github.com/mihonapp/mihon/commit/21145144cdf550aa775047603e06e261951ebc42))
- Navigation layout tap zones shifting after zooming out in webtoon readers ([@FooIbar](https://github.com/FooIbar)) ([#767](https://github.com/mihonapp/mihon/pull/767))
- Some extension not loading due to missing classes ([@AwkwardPeak7](https://github.com/AwkwardPeak7)) ([#783](https://github.com/mihonapp/mihon/pull/783))
- Theme colors in accordance to upstream changes ([@CrepeTF](https://github.com/CrepeTF), [@AntsyLich](https://github.com/AntsyLich)) ([#766](https://github.com/mihonapp/mihon/pull/766), [#963](https://github.com/mihonapp/mihon/pull/963), [#976](https://github.com/mihonapp/mihon/pull/976))
- Crash when requesting folder access on non-conforming devices ([@mainrs](https://github.com/mainrs)) ([#726](https://github.com/mihonapp/mihon/pull/726))
- Bugged color for Date/Scanlator in chapter list for read chapters ([@ivaniskandar](https://github.com/ivaniskandar)) ([`15d9992`](https://github.com/mihonapp/mihon/commit/15d999229fcce865001d5fa77d0163e6e80e38db))
- Categories having same `order` after restoring backup ([@Cologler](https://github.com/Cologler)) ([`119bcbf`](https://github.com/mihonapp/mihon/commit/119bcbf8ed2415664922ea77fadf0da1165d1732))
- Filter by "Tracking" temporarily stuck after signing out of tracker ([@AntsyLich](https://github.com/AntsyLich)) ([#987](https://github.com/mihonapp/mihon/pull/987))
- JXL image downloading and loading ([@FooIbar](https://github.com/FooIbar)) ([#993](https://github.com/mihonapp/mihon/pull/993))
- Crash when using `%` in category name ([@Animeboynz](https://github.com/Animeboynz), [@FooIbar](https://github.com/FooIbar)) ([#1030](https://github.com/mihonapp/mihon/pull/1030))
- Library is backed up while being disabled ([@AntsyLich](https://github.com/AntsyLich)) ([`56fb4f6`](https://github.com/mihonapp/mihon/commit/56fb4f62a152e87a71892aa68c78cac51a2c8596))
- Crash on list with 0 item but only sticky header ([@cuong-tran](https://github.com/cuong-tran)) ([#1083](https://github.com/mihonapp/mihon/pull/1083))
- Crash when trying to clear cookies of some source ([@FooIbar](https://github.com/FooIbar)) ([#1084](https://github.com/mihonapp/mihon/pull/1084))
- MAL search results not showing start dates ([@MajorTanya](https://github.com/MajorTanya)) ([#1098](https://github.com/mihonapp/mihon/pull/1098))
- Android SDK 35 API collision ([@AntsyLich](https://github.com/AntsyLich)) ([`fdb9617`](https://github.com/mihonapp/mihon/commit/fdb96179c6373eb0a8e7d6daea671a315d5ce5f0))

### Other
- Code cleanup
  - Minor refactor of theming when expressions ([@MajorTanya](https://github.com/MajorTanya)) ([#396](https://github.com/mihonapp/mihon/pull/396)) 
  - Inside `WorkerInfoScreen` ([@AntsyLich](https://github.com/AntsyLich)) ([`5aec8f8`](https://github.com/mihonapp/mihon/commit/5aec8f8018236a38106483da08f9cbc28261ac9b))
  - Inside `ChapterDownloadIndicator`, `MangaChapterListItem` ([@AntsyLich](https://github.com/AntsyLich)) ([`b7e091d`](https://github.com/mihonapp/mihon/commit/b7e091d5d039e00cababc7daf555280df6cf9c03))
- Address `overridePendingTransition` deprecation ([@MajorTanya](https://github.com/MajorTanya)) ([#410](https://github.com/mihonapp/mihon/pull/410))
- Prioritize extension classes and files over app ([@beer-psi](https://github.com/beer-psi)) ([#433](https://github.com/mihonapp/mihon/pull/433))
- Use compose pager implementation ([@ivaniskandar](https://github.com/ivaniskandar)) ([`84984ef`](https://github.com/mihonapp/mihon/commit/84984ef7e1d7242924120cd2f171cb9dd75bc916))
- Switch to coil3 from coil2 ([@ivaniskandar](https://github.com/ivaniskandar)) ([`f72b6e4`](https://github.com/mihonapp/mihon/commit/f72b6e4d7c1f2f93d705402e4d80c94160bef54d))
  - Fix GIF not playing ([@jobobby04](https://github.com/jobobby04)) ([`59bedb3`](https://github.com/mihonapp/mihon/commit/59bedb33ff59ad5db1df2e93567a2266fb63eacc))
- Accommodate db for sync support ([@kaiserbh](https://github.com/kaiserbh)) ([#450](https://github.com/mihonapp/mihon/pull/450))
- Fix webtoon last visible item position calculation ([@FooIbar](https://github.com/FooIbar)) ([#562](https://github.com/mihonapp/mihon/pull/562))
- Migrate from `com.google.accompanist:accompanist-webview` to `io.github.kevinnzou:compose-webview` ([@sirlag](https://github.com/sirlag)) ([#569](https://github.com/mihonapp/mihon/pull/569))

## [v0.16.5] - 2024-04-09
### Added
- Relative date for up to a week in the future ([@sirlag](https://github.com/sirlag)) ([#415](https://github.com/mihonapp/mihon/pull/415))
- Advance setting to install custom color profiles ([@wwww-wwww](https://github.com/wwww-wwww)) ([#523](https://github.com/mihonapp/mihon/pull/523))

### Changed
- Permanently enable 32-bit color mode ([@wwww-wwww](https://github.com/wwww-wwww)) ([#523](https://github.com/mihonapp/mihon/pull/523))

### Fixed
- Wrong dates in Updates and History tab due to time zone issues ([@sirlag](https://github.com/sirlag)) ([#402](https://github.com/mihonapp/mihon/pull/402))
  - Fix extra date header introduced by parent PR ([@sirlag](https://github.com/sirlag)) ([#415](https://github.com/mihonapp/mihon/pull/415))
  - Fix build time in about screen displayed in UTC ([@AntsyLich](https://github.com/AntsyLich)) ([`aed53d3`](https://github.com/mihonapp/mihon/commit/aed53d3bdc85ce0e899fbb90b9f9cad0f1b86480))
- App infinitely retries tracker update instead of failing after 3 tries ([@MajorTanya](https://github.com/MajorTanya)) ([#411](https://github.com/mihonapp/mihon/pull/411))
- Crash on Pixel devices (was introduced due to compose update) ([`ab06720`](https://github.com/mihonapp/mihon/commit/ab067209661eceefc04c65f6bdbfcaa8a1264651))
- Crash when opening some heif/heic images ([@az4521](https://github.com/az4521)) ([#466](https://github.com/mihonapp/mihon/pull/466))
- Crash when putting app in background while track date selection dialog is open ([@ivaniskandar](https://github.com/ivaniskandar)) ([`c348fac`](https://github.com/mihonapp/mihon/commit/c348fac78fac479fb123bd617c01c78b9ca851d5))
- Dates for saved images not following the specification (fixes date issue mainly on Samsung devices) ([@MajorTanya](https://github.com/MajorTanya)) ([#552](https://github.com/mihonapp/mihon/pull/552))
- Colors getting distorted when opening CMYK jpeg images ([@wwww-wwww](https://github.com/wwww-wwww)) ([#523](https://github.com/mihonapp/mihon/pull/523))

## [v0.16.4] - 2024-02-27
### Changed
- Don't include custom user agent for MAL (circumvents MAL block) ([@AntsyLich](https://github.com/AntsyLich)) ([`085ad8d`](https://github.com/mihonapp/mihon/commit/085ad8d44637c375a8ed24aba3a6f75f5b0cc9ee))

## [v0.16.3] - 2024-01-30
### Added
- Copy extension debug info when clicking logo or name in the extension details screen ([@MajorTanya](https://github.com/MajorTanya)) ([#271](https://github.com/mihonapp/mihon/pull/271))

### Changed
- Hide display cutoff setting in reader settings sheet if fullscreen is disabled ([@Riztard](https://github.com/Riztard)) ([#241](https://github.com/mihonapp/mihon/pull/241))
- Library update error filename to `mihon_update_errors.txt` from `tachiyomi_update_errors.txt` ([@mjishnu](https://github.com/mjishnu)) ([#253](https://github.com/mihonapp/mihon/pull/253))

### Fixed
- Bottom sheet UI issues on non-tablet devices ([@theolm](https://github.com/theolm)) ([#182](https://github.com/mihonapp/mihon/pull/182))
- Crash when switching screen while a list is scrolling ([@theolm](https://github.com/theolm)) ([#272](https://github.com/mihonapp/mihon/pull/272))
- Newly installed extensions not being recognized by Mihon ([@AwkwardPeak7](https://github.com/AwkwardPeak7)) ([#275](https://github.com/mihonapp/mihon/pull/275))
- Failing to refresh MAL token being inferred as token expiration ([@AntsyLich](https://github.com/AntsyLich)) ([`0f4de03`](https://github.com/mihonapp/mihon/commit/0f4de03d7a77b52490dc9a95e96a308b93b26e4f))

### Other
- Add `detekt` (kotlin code analyzer) to the project ([@theolm](https://github.com/theolm)) ([#216](https://github.com/mihonapp/mihon/pull/216))

## [v0.16.2] - 2024-01-28
### Changed
- Backup now contains scanlator filter of a series ([@jobobby04](https://github.com/jobobby04)) ([#166](https://github.com/mihonapp/mihon/pull/166))
- App icon scaling ([@AntsyLich](https://github.com/AntsyLich)) ([`26815c7`](https://github.com/mihonapp/mihon/commit/26815c7356111394665467c1e81255ac9ee33c1a))
- Tracker OAuth client to Mihon's (fixes login issue for Shikimori tracker) ([@AntsyLich](https://github.com/AntsyLich)) ([`e3f33e2`](https://github.com/mihonapp/mihon/commit/e3f33e24f5e928ac8a85d1f500fd42d4715fc6b5))
- Tracker user agents ([@AntsyLich](https://github.com/AntsyLich), [@kitsumed](https://github.com/kitsumed)) ([`e3f33e2`](https://github.com/mihonapp/mihon/commit/e3f33e24f5e928ac8a85d1f500fd42d4715fc6b5))
- Crash log filename to `mihon_crash_logs.txt` from `tachiyomi_crash_logs.txt` ([@MajorTanya](https://github.com/MajorTanya)) ([#234](https://github.com/mihonapp/mihon/pull/234))
- Don't try to refresh MAL token after refresh token expires ([@AntsyLich](https://github.com/AntsyLich)) ([`32188f9`](https://github.com/mihonapp/mihon/commit/32188f9f65009a18250674ef1bd6e57d351c1fba))

### Fixed
- "Flash screen on page change" making the screen full black ([@AntsyLich](https://github.com/AntsyLich)) ([`38d6ab8`](https://github.com/mihonapp/mihon/commit/38d6ab80ce868707829dbc81de4170afe3c2f2a5))
- Faulty MangaUpdates score in database ([@AntsyLich](https://github.com/AntsyLich) ([`a024218`](https://github.com/mihonapp/mihon/commit/a024218410953a389b8af4880fa7ae6cc30124a2)
- Updating extension not reflecting correctly ([@AntsyLich](https://github.com/AntsyLich)) ([`cb06898`](https://github.com/mihonapp/mihon/commit/cb068984303f811692531bf6f14902ae118d8ac7))
- Inconsistent button height in "Data and storage" for some languages ([@theolm](https://github.com/theolm)) ([#202](https://github.com/mihonapp/mihon/pull/202))
- Chapter not being marked as read locally when refreshing Enhanced Trackers ([@Secozzi](https://github.com/Secozzi)) ([#219](https://github.com/mihonapp/mihon/pull/219))

### Other
- Make `last_modified_at` field in database be `0` on insert ([@kaiserbh](https://github.com/kaiserbh)) ([#113](https://github.com/mihonapp/mihon/pull/113))
- Remove usage of `.not()` where possible in code ([@AntsyLich](https://github.com/AntsyLich)) ([`3940740`](https://github.com/mihonapp/mihon/commit/39407407f282dbb7fa972b12053c26b3e3bd66d8))
- Use type-safe project accessors ([@theolm](https://github.com/theolm)) ([#194](https://github.com/mihonapp/mihon/pull/194))
- Legacy tracker model properties now has the same type as the domain ones ([@AntsyLich](https://github.com/AntsyLich)) ([#245](https://github.com/mihonapp/mihon/pull/245))

## [v0.16.1] - 2024-01-18
### Changed
- Branding to Mihon (for references we missed) ([@AntsyLich](https://github.com/AntsyLich)) ([`6539406`](https://github.com/mihonapp/mihon/commit/653940613d661eb371aab3b3c3a8181e4e308c43))
- Preview builds are now called Beta builds ([@AntsyLich](https://github.com/AntsyLich)) ([`3c3a1cd`](https://github.com/mihonapp/mihon/commit/3c3a1cd448ab1f653ddd12b2afe0cba38968d1b9))

### Fixed
- App icon not following the [specification](https://developer.android.com/develop/ui/views/launch/icon_design_adaptive) ([@AntsyLich](https://github.com/AntsyLich)) ([`1849715`](https://github.com/mihonapp/mihon/commit/18497154183356bb0d469b27827f9f7d6b7a3130))
- MangaUpdates default score being set to -1.0 ([@AntsyLich](https://github.com/AntsyLich)) ([`99fd273`](https://github.com/mihonapp/mihon/commit/99fd2731f5d9d374700e89fa67d4d5bf611bbafa))

## [v0.16.0] - 2024-01-16
### Changed
- Branding to Mihon ([@AntsyLich](https://github.com/AntsyLich))
- Minimum supported Android version to 8 ([@AntsyLich](https://github.com/AntsyLich)) ([`dfb3091`](https://github.com/mihonapp/mihon/commit/dfb3091e380dda3e9bfb64bf5c9a685cf3a03d0e))

[unreleased]: https://github.com/mihonapp/mihon/compare/v0.16.5...main
[v0.16.5]: https://github.com/mihonapp/mihon/compare/v0.16.4...v0.16.5
[v0.16.4]: https://github.com/mihonapp/mihon/compare/v0.16.3...v0.16.4
[v0.16.3]: https://github.com/mihonapp/mihon/compare/v0.16.2...v0.16.3
[v0.16.2]: https://github.com/mihonapp/mihon/compare/v0.16.1...v0.16.2
[v0.16.1]: https://github.com/mihonapp/mihon/compare/v0.16.0...v0.16.1
[v0.16.0]: https://github.com/mihonapp/mihon/compare/a9c7cbf...v0.16.0
