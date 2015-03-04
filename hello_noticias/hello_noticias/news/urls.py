from django.conf.urls import patterns, url
from hello_noticias.news import views

urlpatterns = patterns('hello_noticias.news.views',
    url(r'^$', views.NewsIndex.as_view(), name='news_index'),
    url(r'^update/(?P<new_id>\d+)/$', views.NewsUpdate.as_view(), name='news_update'),
    url(r'^list/$', views.NewsList.as_view(), name='news_list'),
    url(r'^create/$', views.NewsCreate.as_view(), name='news_create')
)
