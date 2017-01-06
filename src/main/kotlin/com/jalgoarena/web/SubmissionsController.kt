package com.jalgoarena.web

import com.jalgoarena.data.SubmissionsRepository
import com.jalgoarena.domain.Submission
import com.jalgoarena.domain.User
import org.springframework.web.bind.annotation.*
import javax.inject.Inject

@CrossOrigin
@RestController
class SubmissionsController(
        @Inject private val repository: SubmissionsRepository,
        @Inject private val usersClient: UsersClient) {

    @GetMapping("/submissions", produces = arrayOf("application/json"))
    fun submissions(@RequestHeader("X-Authorization") token: String): List<Submission> {
        val user = usersClient.findUser(token)
        checkForAdmin(user)
        return repository.findAll()
    }

    @GetMapping("/submissions/{userId}", produces = arrayOf("application/json"))
    fun submissions(@PathVariable userId: String, @RequestHeader("X-Authorization") token: String): List<Submission> {
        val user = usersClient.findUser(token)
        confirmUserId(user, userId)
        return repository.findByUserId(userId)
    }

    @PostMapping("/submissions", produces = arrayOf("application/json"))
    fun addOrUpdateSubmission(@RequestBody submission: Submission, @RequestHeader("X-Authorization") token: String): Submission {
        val user = usersClient.findUser(token)
        confirmUserId(user, submission.userId)
        return repository.addOrUpdate(submission)
    }

    @PostMapping("/submissions/delete/{submissionId}", produces = arrayOf("application/json"))
    fun deleteSubmission(@PathVariable submissionId: String, @RequestHeader("X-Authorization") token: String): Boolean {
        val user = usersClient.findUser(token)
        checkForAdmin(user)
        return repository.delete(submissionId)
    }

    private fun checkForAdmin(user: User) {
        if ("ADMIN" != user.role) {
            throw PermissionException()
        }
    }
    private fun confirmUserId(user: User, userId: String) {
        if (user.id != userId) {
            throw PermissionException()
        }
    }
}
