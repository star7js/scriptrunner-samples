import com.atlassian.confluence.pages.PageManager
import com.atlassian.confluence.spaces.SpaceManager
import com.atlassian.confluence.user.UserAccessor
import com.atlassian.sal.api.component.ComponentLocator

def pageManager = ComponentLocator.getComponent(PageManager)
def spaceManager = ComponentLocator.getComponent(SpaceManager)
def userManager = ComponentLocator.getComponent(UserAccessor)

String username = params[0]
String spaceKey = params[1]
def user = userManager.getUserByName(username)

def space = spaceManager.getSpace(spaceKey)

def pageIdsCommentedByUser = pageManager.getPages(space, true).collect { page ->
    page.comments.findAll { it.creator.key == user.key }*.idAsString
}.flatten()

pageIdsCommentedByUser
