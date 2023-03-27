package it.com.adaptavist

import com.atlassian.jira.bc.project.ProjectCreationData
import com.atlassian.jira.bc.project.ProjectService
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.project.AssigneeTypes
import com.atlassian.jira.project.ProjectManager
import com.atlassian.jira.project.type.ProjectTypeKeys
import com.atlassian.jira.user.ApplicationUser
import spock.lang.Shared
import spock.lang.Specification

class CustomEventListenerIT extends Specification {

    @Shared
    ProjectService projectService = ComponentAccessor.getComponent(ProjectService)

    @Shared
    ProjectManager projectManager = ComponentAccessor.projectManager

    @Shared
    ApplicationUser currentUser = ComponentAccessor.jiraAuthenticationContext.loggedInUser

    def "project description updates upon project creation"() {
        setup:
        def projectName = "FooBar Project"
        def initialDescription = "initial description"
        def expectedDescription = "foo bar description"

        and:
        def projectCreationData = new ProjectCreationData.Builder().with {
            withName(projectName)
            withKey("FOOBAR")
            withDescription(initialDescription)
            withLead(currentUser)
            withUrl(null)
            withAssigneeType(AssigneeTypes.PROJECT_LEAD)
            withType(ProjectTypeKeys.BUSINESS)
        }.build()

        when:
        def createProjectValidationResult = projectService.validateCreateProject(currentUser, projectCreationData)

        then:
        !createProjectValidationResult.errorCollection.hasAnyErrors()

        when:
        projectService.createProject(createProjectValidationResult)

        then:
        projectManager.getProjectObjByName(projectName).description == expectedDescription
    }
}
