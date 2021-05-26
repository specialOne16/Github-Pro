package com.jundapp.githubpro.core.di

import androidx.room.Room
import com.jundapp.githubpro.core.data.UserRepository
import com.jundapp.githubpro.core.data.source.local.LocalDataSource
import com.jundapp.githubpro.core.data.source.local.room.UserDatabase
import com.jundapp.githubpro.core.data.source.remote.RemoteDataSource
import com.jundapp.githubpro.core.data.source.remote.network.ApiService
import com.jundapp.githubpro.core.domain.repository.IUserRepository
import com.jundapp.githubpro.core.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object CoreModule {
    val databaseModule = module {
        factory { get<UserDatabase>().userDao() }
        single {
            val passphrase: ByteArray = SQLiteDatabase.getBytes("jundapp".toCharArray())
            val factory = SupportFactory(passphrase)
            Room.databaseBuilder(
                androidContext(),
                UserDatabase::class.java, "user.db"
            ).fallbackToDestructiveMigration()
                .openHelperFactory(factory)
                .build()
        }
    }

    val networkModule = module {
        single {
            val hostname = "api.github.com"
            val certificatePinner = CertificatePinner.Builder()
                .add(hostname, "sha256/azE5Ew0LGsMgkYqiDpYay0olLAS8cxxNGUZ8OJU756k=")
                .add(hostname, "sha256/vnCogm4QYze/Bc9r88xdA6NTQY74p4BAz2w5gxkLG2M=")
                .add(hostname, "sha256/WoiWRyIOVNa9ihaBciRSC7XHjliYS9VwUGOIud4PB18=")
                .build()
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .certificatePinner(certificatePinner)
                .build()
        }
        single {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(get())
                .build()
            retrofit.create(ApiService::class.java)
        }
    }

    val repositoryModule = module {
        single { LocalDataSource(get()) }
        single { RemoteDataSource(get()) }
        factory { AppExecutors() }
        single<IUserRepository> { UserRepository(get(), get(), get()) }
    }
}