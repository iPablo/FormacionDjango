from django.conf.urls import patterns, include, url

from django.contrib import admin
admin.autodiscover()

urlpatterns = patterns('',
    url(r'^$', include('hello_noticias.hello_world.urls')),
    url(r'^news/', include('hello_noticias.news.urls')),
    url(r'^admin/', include(admin.site.urls)),
)
