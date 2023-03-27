package it.com.adaptavist

import com.atlassian.bitbucket.project.Project
import com.atlassian.bitbucket.project.ProjectCreateRequest
import com.atlassian.bitbucket.project.ProjectCreationCanceledException
import com.atlassian.bitbucket.project.ProjectService
import com.atlassian.sal.api.component.ComponentLocator
import spock.lang.Shared
import spock.lang.Specification

class ProjectNamingConventionListenerIT extends Specification {

    @Shared
    ProjectService projectService = ComponentLocator.getComponent(ProjectService)

    def "project naming convention built-in listener"() {
        setup:
        def illegalName = "SampleProject"
        def legalName = "FOOSampleProject"

        when:
        createProject(illegalName, "ILLEGAL")

        then:
        thrown(ProjectCreationCanceledException)

        and:
        !projectService.getByName(illegalName)

        when:
        createProject(legalName, "LEGAL")

        then:
        projectService.getByName(legalName)
    }

    Project createProject(String projectName, String projectKey) {
        def projectCreateRequest = new ProjectCreateRequest.Builder().key(projectKey).name(projectName).build()
        projectService.create(projectCreateRequest)
    }
}

