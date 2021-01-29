# OrmaSample
sample for Orma


# 動作確認エビデンス

## マイグレーション
バージョン１から2へのマーグレーション
Table名を TodoからMemoに変更
カラム名をcontentからmemoに変更

- マイグレーション前
<img width="1288" alt="スクリーンショット 2020-04-25 16 39 42" src="https://user-images.githubusercontent.com/16476224/80274335-90ffcb80-8714-11ea-9fb9-2424340d0624.png">


- マイグレーション後
<img width="1295" alt="スクリーンショット 2020-04-25 16 46 14" src="https://user-images.githubusercontent.com/16476224/80274301-58f88880-8714-11ea-900c-d58f53d9b10f.png">

## トランザクション

- transactionSyncあり

割り込みのレコードがtransaction処理後にレコード追加されていることを確認
<img width="1046" alt="スクリーンショット 2020-04-27 9 16 34" src="https://user-images.githubusercontent.com/16476224/80323665-cc5edf00-8867-11ea-968f-a5d510fae7f6.png">

- transactionSyncなし

割り込みのレコードがtransaction処理中にレコード追加されていることを確認
<img width="1047" alt="スクリーンショット 2020-04-27 9 18 04" src="https://user-images.githubusercontent.com/16476224/80323709-0203c800-8868-11ea-8f4b-16f63b932a90.png">
