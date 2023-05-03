package com.adaptavist

import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.event.ProjectCreatedEvent
import com.atlassian.jira.project.UpdateProjectParameters

def event = event as ProjectCreatedEvent
def updateProjectParameters = UpdateProjectParameters.forProject(event.project.id).description("foo bar description")
ComponentAccessor.projectManager.updateProject(updateProjectParameters)