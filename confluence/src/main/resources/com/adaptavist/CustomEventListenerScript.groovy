package com.adaptavist

import com.atlassian.confluence.event.events.space.SpaceCreateEvent

def event = event as SpaceCreateEvent
def space = event.space
space.description.setBodyAsString("foo bar description")