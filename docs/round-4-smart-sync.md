# Round 4 - Smart Sync

This round turns the sync prototype into a more realistic online/offline sync flow.

## What Changed

- Added Android `ACCESS_NETWORK_STATE` permission.
- Added runtime network availability checks through `ConnectivityManager`.
- Added Smart Sync status in the Sync Center.
- Added a Smart Sync action that checks:
  - network availability,
  - cloud backend URL,
  - pending local sync queue.
- If network/backend is unavailable, the app keeps a pending sync record in SQLite.
- If network/backend is available, the app sends the full sync payload to the backend and marks local queue items as synced.

## Prototype Scope

This is still a foreground prototype flow. It does not yet run WorkManager background jobs, but it now models the real sync decision path:

1. collect local SQLite changes,
2. check network,
3. check backend endpoint,
4. upload payload,
5. mark queue complete only after backend success,
6. preserve failed attempts for later retry.

## Next Production Step

The next production step is to move Smart Sync into WorkManager with exponential backoff and backend conflict resolution.
