package com.nameisjayant.noteappcompose.di

import com.nameisjayant.noteappcompose.data.repository.NoteRepositoryImpl
import com.nameisjayant.noteappcompose.features.domain.repository.NoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesNoteRepository(
        noteRepositoryImpl: NoteRepositoryImpl
    ): NoteRepository
}

