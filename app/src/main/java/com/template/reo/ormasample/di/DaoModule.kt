package com.template.reo.ormasample.di

import android.content.Context
import android.util.Log
import com.github.gfx.android.orma.AccessThreadConstraint
import com.github.gfx.android.orma.migration.ManualStepMigration
import com.template.reo.ormasample.orma.OrmaDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DaoModule {

    const val MIGRATION_VERSION_2 = 2

    /**
     * Ormaの操作は OrmaDatabaseクラスのインスタンス経由で行います。
     * これをDagger2などのDIで @Singleton にしておくことをおすすめします。
     * デフォルトの設定だと、デバッグビルド時に実行するSQLをlogcatに出力したりメインスレッドでの書き込みで例外を投げたりします。
     * これらはbuilderの設定で変更できます。
     */
    @Singleton
    @Provides
    fun providesOrmaDatabase(context: Context): OrmaDatabase {
        // AccessThreadConstraint.WARNING　を指定しているので、メインスレッドでCRUD操作すると以下のワーニングLogでる.
        // W/Orma: xxx things must run in background
        return OrmaDatabase.builder(context)
            .writeOnMainThread(AccessThreadConstraint.WARNING)
            .readOnMainThread(AccessThreadConstraint.WARNING)
            .migrationStep(MIGRATION_VERSION_2, object : ManualStepMigration.ChangeStep() {
                override fun change(helper: ManualStepMigration.Helper) {
                    Log.d(
                        "migrationStep",
                        "upgrade: " + helper.upgrade + " version: " + helper.version
                    )
                    helper.renameColumn("Todo", "content", "memo")
                    helper.renameTable("Todo", "Memo")
                }
            })
            .build()
    }
}