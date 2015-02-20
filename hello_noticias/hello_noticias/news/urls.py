from django.conf.urls import patterns, url

from hello_noticias.news import views

urlpatterns = patterns('',
    url(r'^$', views.mydb, name='mydb'),
)
