package com.adaptavist

import com.atlassian.bitbucket.hook.repository.CommitAddedDetails
import com.atlassian.bitbucket.hook.repository.RepositoryHookCommitFilter
import com.atlassian.bitbucket.hook.repository.RepositoryHookResult
import com.atlassian.bitbucket.hook.repository.PreRepositoryHookCommitCallback

import javax.annotation.Nonnull

commitCallback = new PreRepositoryHookCommitCallback() {

    @Override
    boolean onCommitAdded(@Nonnull CommitAddedDetails commitDetails) {
        def commit = commitDetails.commit
        def commitHasExeFile = commit.pathsMatch("glob:**.exe")
        if (commitHasExeFile) {
            resultBuilder.veto("Contains invalid files.", "$commit.displayId - Contains files with .exe extension.")

            return false
        }

        return true
    }

    @Override
    RepositoryHookResult getResult() {
        resultBuilder.build()
    }
}

commitFilters << RepositoryHookCommitFilter.ADDED_TO_ANY_REF

return RepositoryHookResult.accepted()
