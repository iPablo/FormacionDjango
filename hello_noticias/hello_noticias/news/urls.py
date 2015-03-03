from django.conf.urls import patterns, url

urlpatterns = patterns('hello_noticias.news.views',
    url(r'^$', 'index', name='index'),
    url(r'^update/(?P<new_id>\d+)', 'update', name='update'),
    url(r'^update/', 'update_get', name='update_get'),
    url(r'^create/', 'create', name='create')
)
