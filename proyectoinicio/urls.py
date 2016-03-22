from django.conf.urls import url

from . import views

app_name="proyectoinicio"
urlpatterns = [
    url(r'^$', views.index, name='index'),
    url(r'^v1/noticias$', views.dameNoticias, name='noticias'),
    url(r'^v1/eventos$', views.dameEventos, name='eventos'),
    url(r'^v1/todo$', views.dameTodo, name='todo'),
    url(r'^v1/ampliar/(?P<noticia_pk>[0-9]+)$', views.vistaNoticia, name='ampliar'),
    url(r'^v1/borrar/(?P<noticia_pk>[0-9]+)$', views.borraNoticia, name='borrar'),
    url(r'^v1/editar/(?P<noticia_pk>[0-9]+)$', views.editaNoticia, name='editar'),
    url(r'^v1/confirmar/(?P<noticia_pk>[0-9]+)$', views.editar, name='confirmar'),
    url(r'^v1/crear/$', views.creaNoticia, name='crearNoticia'),
    url(r'^v1/creada/$', views.crear, name='crear'),
]
