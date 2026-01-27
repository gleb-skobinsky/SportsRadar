package com.sportsradar.features.notes

import com.sportsradar.features.notes.data.NotesRepository
import com.sportsradar.features.notes.data.NotesRepositoryImpl
import org.koin.dsl.module

val notesModule = module {
    single<NotesRepository> {
        NotesRepositoryImpl(get())
    }
}