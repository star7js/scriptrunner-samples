package com.adaptavist

import com.atlassian.confluence.event.events.space.SpaceCreateEvent
import com.atlassian.confluence.spaces.SpaceManager
import com.atlassian.sal.api.component.ComponentLocator

def spaceManager = ComponentLocator.getComponent(SpaceManager)

def event = event as SpaceCreateEvent
def space = event.space
space.description.setBodyAsString("foo bar description")
spaceManager.saveSpace(space)