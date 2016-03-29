from django.conf.urls import url

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
]
