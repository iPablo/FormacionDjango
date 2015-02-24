from django.conf.urls import patterns, url

from hello_noticias.news import views

urlpatterns = patterns('',
    url(r'^$', views.index, name='index'),
    url(r'^update/(?P<id_new>\d+)', views.update, name='update'),
    url(r'^update/', views.update_get, name='update_get'),
    url(r'^create/', views.create, name='create'),
    url(r'^succes_create/', views.succes_create, name='succes_create'),
    url(r'^update_done/', views.update_done, name='update_done')
)
