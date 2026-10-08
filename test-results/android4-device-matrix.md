# Android 4 - Device matrix

Test class: `ConversationTests`

| # | Device | Android / API | Resolution | Type | Scenario 1 | Scenario 2 | Scenario 3 |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | Pixel_6 | Android 13 / API 33 | 1080x2400 | Emulator | Pass | Pass | Pass |
| 2 | Pixel_5 | Android 14 / API 34 | 1080x2340 | Emulator | Pass | Pass | Pass |
| 3 | Pixel_8_Pro | Android 15 / API 35 | 1344x2992 | Emulator | Pass | Pass | Pass |

Scenario 1 - `sentMessageRemainsInJohnWeekChatAfterReopening`
Scenario 2 - `sentQuestionIsDisplayedInOwnChat`
Scenario 3 - `conversationIsContinuedByAnotherAccount`

## Test data

- User A: `user_a`
- User B: `user_b`
- Chats: `johnWeek`, `Nikoloz Makharadze chat`, `something`

No physical device was used, all three are emulators.