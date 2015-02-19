from django.conf.urls import patterns, include, url

from django.contrib import admin
admin.autodiscover()

urlpatterns = patterns('',
    # Examples:
    # url(r'^$', 'hello_noticias.views.home', name='home'),
    # url(r'^blog/', include('blog.urls')),
    url(r'^$', include('hello_noticias.hello_world.urls')),
    url(r'^admin/', include(admin.site.urls)),
)
