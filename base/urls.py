#!/usr/bin/env python
# -*- coding: utf-8 -*-
from django.conf.urls import url
from base.views import *
from rest_framework.urlpatterns import format_suffix_patterns

from . import views

app_name = 'base'
urlpatterns = [

    url(r'^$', views.index, name='index'),
    url(r'^v1/news/$', views.obtener_news, name='obtener_news'),
    url(r'^v1/events/$', views.obtener_events, name='obtener_events'),
    url(r'^v1/todos/$', views.obtener_todo, name='obtener_todo'),
    url(r'^v1/news/(?P<new_id>[0-9]+)/$', views.detail_news, name='detail_news'),
    url(r'^v1/events/(?P<event_id>[0-9]+)/$', views.detail_events, name='detail_events'),
    url(r'^v1/news/create/$', views.create_new, name='create_new'),
    url(r'^v1/news/update/(?P<new_id>[0-9]+)/$', views.update_new, name='update_new'),
    url(r'^v1/news/delete/(?P<new_id>[0-9]+)/$', views.delete_new, name='delete_new'),
    url(r'^v1/events/create/$', views.create_event, name='create_event'),
    url(r'^v1/events/update/(?P<event_id>[0-9]+)/$', views.update_event, name='update_event'),
    url(r'^v1/events/delete/(?P<event_id>[0-9]+)/$', views.delete_event, name='delete_event'),

    url(r'^v2/todos/$', TodosList.as_view(), name='TodosList'),
    url(r'^v2/news/$', NewsItemList.as_view(), name='NewsItemList'),
    url(r'^v2/news/(?P<pk>[0-9]+)/$', NewsItemDetail.as_view(), name='NewsItemDetail'),
    url(r'^v2/events/$', EventList.as_view(), name='EventList'),
    url(r'^v2/events/(?P<pk>[0-9]+)/$', EventsDetail.as_view(), name='EventsDetail'),
    url(r'^v2/news/create/$', NewsCreate.as_view(), name='NewsCreate'),
    url(r'^v2/events/create/$', EventsCreate.as_view(), name='EventsCreate'),
    url(r'^v2/news/update/(?P<pk>[0-9]+)/$', NewsUpdate.as_view(), name='NewsUpdate'),
    url(r'^v2/events/update/(?P<pk>[0-9]+)/$', EventsUpdate.as_view(), name='EventsUpdate'),
    url(r'^v2/news/delete/(?P<pk>[0-9]+)/$', NewsDelete.as_view(), name='NewsDelete'),
    url(r'^v2/events/delete/(?P<pk>[0-9]+)/$', EventsDelete.as_view(), name='EventsDelete'),

    url(r'^API/news/$', views.NewsItemListAPI.as_view(), name='NewsItemListAPI'),
    url(r'^API/news/(?P<pk>[0-9]+)/$', views.NewsItemDetailAPI.as_view(), name='NewsItemDetailAPI'),
    url(r'^API/events/$', views.EventsListAPI.as_view(), name='EventsListAPI'),
    url(r'^API/events/(?P<pk>[0-9]+)/$', views.EventsDetailAPI.as_view(), name='EventsDetailAPI'),
]
