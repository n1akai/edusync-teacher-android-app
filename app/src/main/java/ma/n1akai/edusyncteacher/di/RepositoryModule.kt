package ma.n1akai.edusyncteacher.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ma.n1akai.edusyncteacher.data.network.AuthApi
import ma.n1akai.edusyncteacher.data.network.TeacherApi
import ma.n1akai.edusyncteacher.data.repository.AuthRepository
import ma.n1akai.edusyncteacher.data.repository.StudentRepository
import ma.n1akai.edusyncteacher.data.repository.TeacherRepository

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideAuthRepository(authApi: AuthApi) : AuthRepository {
        return AuthRepository(authApi)
    }

    @Provides
    fun provideTeacherRepository(teacherApi: TeacherApi) : TeacherRepository {
        return TeacherRepository(teacherApi)
    }

    @Provides
    fun provideStudentRepository(teacherApi: TeacherApi) : StudentRepository {
        return StudentRepository(teacherApi)
    }

}