package com.sportsradar.features.notes

import com.sportsradar.features.notes.repository.NotesRepository
import com.sportsradar.features.notes.repository.NotesRepositoryImpl
import org.koin.dsl.module

val notesModule = module {
    single<NotesRepository> {
        NotesRepositoryImpl(get())
    }
}