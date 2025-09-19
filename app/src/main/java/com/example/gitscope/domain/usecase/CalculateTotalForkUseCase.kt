package com.example.gitscope.domain.usecase

import com.example.gitscope.data.model.Repository
import javax.inject.Inject

class CalculateTotalForkUseCase @Inject constructor(

) {
    operator fun invoke(repositories: List<Repository>): Int
    = repositories.sumOf { it.forksCount }
}