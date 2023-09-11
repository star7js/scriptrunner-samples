package com.adaptavist

import com.atlassian.confluence.links.LinkManager
import com.atlassian.sal.api.component.ComponentLocator

/**
 * Stores number of incoming links a page has
 * To be used with the 'Number' field type
 *
 * Example CQL query: Find all pages and sort in descending order by number of incoming links
 *  type = page ORDER BY yourFieldName DESC
 */

def linkManager = ComponentLocator.getComponent(LinkManager)
linkManager.getIncomingLinksToContent(page).size()