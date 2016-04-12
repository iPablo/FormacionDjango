#!/usr/bin/env python
# -*- coding: utf-8 -*-

from django.test import TestCase, RequestFactory
from .models import NewsItem, Event
from .views import creaNoticia, editaNoticia
from django.core.urlresolvers import reverse
import datetime
from django.utils import timezone
from rest_framework import status
from rest_framework.test import APITestCase, APIClient
from django.contrib.auth.models import User


class NewsItemMethodTests(TestCase):

	def test_ha_pasado_con_noticias_futuras(self):
		"""
		El metodo tendria que devolver falso para noticias cuya
		fecha de publicacion este mas de dos dias en el futuro
		"""
		hora = timezone.now() + datetime.timedelta(days=12)
		noticia_futura = NewsItem(publish_date=hora)
		self.assertEqual(noticia_futura.ha_pasado(), False)

	def test_ha_pasado_con_noticias_pasadas(self):
		"""
		El metodo tendria que devolver verdadero para noticias
		cuya fecha de publicacion fuese mucho mas de dos dias
		en el pasado
		"""
		hora = timezone.now() - datetime.timedelta(days=12)
		noticia_pasada = NewsItem(publish_date=hora)
		self.assertEqual(noticia_pasada.ha_pasado(), True)


class EventMethodTests(TestCase):

	def test_dameDuracion_con_fechas_de_misma_fecha(self):
		"""
		dameDuracion() es un metodo que nos devuelve la duracion
		en dias de un evento en forma de entero. Si metiesemos
		la misma fecha, el resultado seria cero.
		"""

		fecha_inicio = timezone.now()+datetime.timedelta(days=1)
		fecha_fin = timezone.now()+datetime.timedelta(days=1)
		evento = Event(start_date=fecha_inicio, end_date=fecha_fin)
		self.assertEqual(evento.dameDuracion(), 0)

	def test_dameDuracion_con_fecha_de_fin_antes(self):
		"""
		Como es obvio, la fecha de fin del evento no puede ser
		antes que la fecha de inicio.
		"""
		fecha_inicio = timezone.now()+datetime.timedelta(days=1)
		fecha_fin = timezone.now()
		evento = Event(start_date=fecha_inicio, end_date=fecha_fin)
		self.assertNotEqual(evento.dameDuracion(), -1)


def create_user(admin):
	"""
	Nos va a crear un usuario que tendra o no permisos de administrador
	"""
	return User.objects.create(username="pepito", password="pepito", is_superuser=admin)


def create_newsitemP(titulo, descripcion, dias):
	"""
	Esto va a publicar una noticia ficticia con los parametros
	titulo y descripcion que le demos. La fecha de publicacion
	sera pasada (los dias)
	"""
	usuario = create_user(True)
	fecha = timezone.now() - datetime.timedelta(days=dias)
	return NewsItem.objects.create(title=titulo, description=descripcion, publish_date=fecha, owner=usuario)


def crear_evento(titulo, descripcion):
	"""
	Una simple funcion para crear eventos de cara al testeo.
	Las fechas seran automaticas (7 dias a partir del actual)
	"""
	usuario = create_user(True)
	fechainicio = timezone.now()
	fechafin = timezone.now()+datetime.timedelta(days=7)
	return Event.objects.create(title=titulo, description=descripcion, start_date=fechainicio, end_date=fechafin, owner=usuario)


class NewsitemViewTests(TestCase):

	def setUp(self):
		# Todos los tests necesitan acceso a la request
		self.factory = RequestFactory()

	def test_create_comment(self):
		"""
		Test para ver si se ha añadido un comentario correctamente.
		"""

	def test_vista_con_todo(self):
		"""
		EN principio como no le pasamos ningun dato, nos tendrian que aparecer
		dos mensajes, uno que no hay noticias y otro, diciendo que no hay
		eventos.
		"""
		response = self.client.get(reverse('proyectoinicio:todo'))
		self.assertContains(response, "No se ha devuelto ninguna noticia")
		self.assertContains(response, "No se ha devuelto ning\xc3\xban evento") # Para saltarme la tilde

	def test_index(self):
		"""
		Comprobamos que el index sea correcto, a ver si nos sale un
		mensajito que nos diga de crear una noticia.
		"""
		response = self.client.get(reverse('proyectoinicio:noticias'))
		self.assertEqual(response.status_code, 200)
		self.assertContains(response, "Quieres crear una noticia")

	def test_index_view_sin_noticias(self):
		"""
		Si no existe ninguna noticia, deberia de aparecer un mensajito
		que nos dijese que no existe ninguna pregunta
		"""

		response = self.client.get(reverse('proyectoinicio:noticias'))
		self.assertEqual(response.status_code, 200)
		self.assertContains(response, "No hay noticias disponibles")  # Lo verifica automaticamente en el HTML
		self.assertQuerysetEqual(response.context['bloque_noticias'], [])

	def test_index_view_con_noticias_pasadas(self):
		"""
		Deberia aparecer un indicador en la pagina que nos dijese
		claramente si la noticia ha ocurrido ya, para no dar casos
		a errores de informacion
		"""
		create_newsitemP(titulo="prueba", descripcion="prueba de pasado", dias=4)
		response = self.client.get(reverse('proyectoinicio:noticias'))
		self.assertEqual(response.status_code, 200)
		self.assertContains(response, "Esta noticia es antigua.")
		self.assertQuerysetEqual(
			response.context['bloque_noticias'],
			['<NewsItem: prueba>']
			)

	def test_vista_ampliada_con_noticias_pasadas(self):
		"""
		De aparecer una noticia pasada en vista ampliada, tendria
		que cambiarse la clase de Bootstrap, y a su vez dar un
		mensajito que nos diga que la noticia no es reciente,
		que se deberia borrar
		"""
		noticia = create_newsitemP(titulo="prueba", descripcion="prueba de pasado", dias=3)
		response = self.client.get(reverse('proyectoinicio:ampliar', kwargs={'noticia_pk': noticia.id}))
		self.assertEqual(response.status_code, 200)
		self.assertContains(response, "Esta noticia no es reciente. Deberias borrarla.")

	def test_vista_ampliada_sin_noticias(self):
		"""
		Si ampliasemos una noticia sin pasarle ninguna ID tendriamos que tener
		un error de estatus 404.
		"""
		response = self.client.get(reverse('proyectoinicio:ampliar', kwargs={'noticia_pk': 11010101010}))
		self.assertEqual(response.status_code, 404)

	def test_vista_actualizada(self):
		"""
		La vista de actualizacion tiene que contener una noticia que le pasemos.
		"""
		noticia = create_newsitemP(titulo="prueba" , descripcion="prueba de pasado", dias=3)
		response = self.client.get(reverse('proyectoinicio:editar', kwargs={'noticia_pk': noticia.id}))
		self.assertContains(response, noticia)

	def test_vista_crear(self):
		"""
		Si se nos crea un objeto y nos redirige a Index, será correcto
		"""
		request = self.factory.post('/v1/crear')
		request.POST['title'] = "Hola"
		request.POST['description'] = "Blablabla"
		request.POST['publish_date'] = timezone.now()
		response = creaNoticia(request)
		self.assertEqual(response.status_code, 200)

	def test_vista_editar(self):
		"""
		Verifiquemos que nos redirige correctamente al editar una noticia
		cualquiera.
		"""
		noticia = create_newsitemP(titulo="prueba" , descripcion="prueba de pasado", dias=3)
		request = self.factory.post('/v1/editar/%s' % (noticia.id))
		request.POST['title'] = "Hola"
		request.POST['description'] = "Blablabla"
		request.POST['publish_date'] = timezone.now()
		response = editaNoticia(request, noticia.id)
		self.assertEqual(response.status_code, 200)

	def test_vista_editar_invalido(self):
		"""
		Le pasamos un campo inválido y si nos redirige donde debe.
		"""
		noticia = create_newsitemP(titulo="prueba", descripcion="prueba de pasado", dias=3)
		request = self.factory.post('/v1/editar/%s' % (noticia.id))
		request.POST['title'] = "Hola"
		request.POST['description'] = "Blablabla"
		request.POST['publish_date'] = "fallo a drede"
		response = editaNoticia(request, noticia.id)
		self.assertEqual(response.status_code, 200)

	def test_VBC_vista(self):
		"""
		Si accedemos a la vista basada en clases, lo primero que tendriamos que tener
		en la respuesta seria un set con la query de todos los objetos NewsItem, es
		decir, no tendria que salir el mensajito de que no hay noticias
		"""

		response = self.client.get(reverse('proyectoinicio:noticiasVBC'))
		self.assertNotEqual(response, 'No hay noticias disponibles')

	def test_VBC_detalle(self):
		"""
		Es la vista por detalles. Para que sea correcta tenemos que tener una noticia dentro y
		per se tenemos que tener un codigo de status 200
		"""
		noticia = create_newsitemP(titulo="prueba", descripcion="prueba de pasado", dias=3)
		response = self.client.get(reverse('proyectoinicio:ampliarVBC', kwargs={'noticia_pk': noticia.id}))
		self.assertEqual(response.status_code, 200)
		self.assertContains(response, noticia)

	def test_VBC_borrado(self):
		"""
		Prueba de la vista basada en clases del borrado. Al nosotros borrar la pagina
		tiene que redirigirnos a una ventana de confirmacion, veamos si lo hace bien
		"""
		noticia = create_newsitemP(titulo="prueba", descripcion="prueba de pasado", dias=3)
		response = self.client.get(reverse('proyectoinicio:borrarVBC', kwargs={'noticia_pk': noticia.id}))
		self.assertContains(response, "seguro")

	def test_VBC_creado(self):
		"""
		Para crear un formulario de creacion de noticias, tenemos que ir a una pagina
		en la que nos compruebe si efectivamente existe.
		"""
		response = self.client.get(reverse('proyectoinicio:crearVBC'))
		self.assertEqual(response.status_code, 200)
		self.assertContains(response, "form")

	def test_VBC_editado(self):
		"""
		Al igual que antes, tenemos que tener tanto un formulario existente
		como el codigo.
		"""
		response = self.client.get(reverse('proyectoinicio:crearVBC'))
		self.assertEqual(response.status_code, 200)
		self.assertContains(response, "form")

	def test_evento_vista(self):
		"""
		Esto es para la vista de eventos. Tendriamos que acceder, pero al devolvernos
		algun evento, la respuesta no puede ser la de no hay eventos disponibles
		"""
		response = self.client.get(reverse('proyectoinicio:eventos'))
		self.assertEqual(response.status_code, 200)
		self.assertNotContains(response, 'No hay eventos disponibles')

	def test_evento_detalle(self):
		"""
		Al coger detalle de un evento, la respuesta en la plantilla de Vista Ampliada
		no puede ser "Ese evento no existe"
		"""
		evento = crear_evento(titulo="prueba", descripcion="prueba de evento")
		response = self.client.get(reverse('proyectoinicio:ampliarEvento', kwargs={'pk': evento.id}))
		self.assertEqual(response.status_code, 200)
		self.assertNotContains(response, 'Ese evento no existe')

	def test_evento_borrar(self):
		"""
		Si borramos un evento nos tiene que redirigir a una pagina de confirmacion si
		lo hacemos bien.
		"""
		evento = crear_evento(titulo="prueba", descripcion="prueba de evento")
		response = self.client.get(reverse('proyectoinicio:borrarEvento', kwargs={'event_pk': evento.id}))
		self.assertEqual(response.status_code, 200)
		self.assertContains(response, 'seguro')

	def test_evento_crear(self):
		"""
		Si creamos un evento tendriamos que tener un formulario que existe.
		"""
		response = self.client.get(reverse('proyectoinicio:crearEvento'))
		self.assertEqual(response.status_code, 200)
		self.assertContains(response, "form")

class APIRestTests(APITestCase):
	def test_crear_noticia_sin_validar(self):
		"""
		Aseguremonos que no podemos crear noticias. Nos deberia
		tirar un error del tipo 403 forbidden
		"""
		noticia = {'title': 'illo',
				'description': 'cabesa',
				'publish_date': '2016-01-01T00:00:00Z',
				'owner': 1}
		response = self.client.post(reverse('proyectoinicio:testNoticias'), noticia, format='json')
		self.assertEqual(response.status_code, status.HTTP_403_FORBIDDEN)

	def test_crear_evento_sin_validar(self):
		"""
		Aseguremonos que no podemos crear eventos. Nos deberia
		tirar un error del tipo 403 forbidden
		"""
		evento = {'title': 'illo',
				'description': 'cabesa',
				'start_date': '2016-01-01T00:00:00Z',
				'end_date': '2016-01-05T00:00:00Z',
				'owner': 1}
		response = self.client.post(reverse('proyectoinicio:testEventos'), evento, format='json')
		self.assertEqual(response.status_code, status.HTTP_403_FORBIDDEN)

	def test_borrar_noticia(self):
		"""
		Nos aseguramos que podemos borrar noticias.
		"""
		usuario = create_user(True)
		client = APIClient()
		client.force_authenticate(usuario)
		noticia = {'title': 'illo',
				'description': 'cabesa',
				'publish_date': '2016-01-01T00:00:00Z',
				'owner': 1}
		response = client.post(reverse('proyectoinicio:testNoticias'), noticia, format='json')
		self.assertEqual(response.status_code, status.HTTP_201_CREATED)
		# Una vez esté creada la noticia podemos ver si se borra.
		response = client.delete(reverse('proyectoinicio:testNoticiasB', kwargs={'pk': 1}), format='json')
		# Pasamos como keyword argument 1, ya que como hemos creado una noticia, la id tendra que ser 1
		self.assertEqual(response.status_code, status.HTTP_204_NO_CONTENT)

	def test_borrar_evento(self):
		"""
		Nos aseguramos que podemos borrar eventos.
		"""
		usuario = create_user(True)
		client = APIClient()
		client.force_authenticate(usuario)
		evento = {'title': 'illo',
				'description': 'cabesa',
				'start_date': '2016-01-01T00:00:00Z',
				'end_date': '2016-01-05T00:00:00Z',
				'owner': 1}
		response = client.post(reverse('proyectoinicio:testEventos'), evento, format='json')
		self.assertEqual(response.status_code, status.HTTP_201_CREATED)
		# Una vez esté creado el evento podemos ver si se borra.
		response = client.delete(reverse('proyectoinicio:testEventosB', kwargs={'pk': 1}), format='json')
		# Pasamos como keyword argument 1, ya que como hemos creado un evento la id tendra que ser 1
		self.assertEqual(response.status_code, status.HTTP_204_NO_CONTENT)

	def test_update_noticia(self):
		"""
		Vamos a ver si acepta una petición PATCH así se updatea la noticia.
		"""
		usuario = create_user(True)
		client = APIClient()
		client.force_authenticate(usuario)
		noticia = {'title': 'illo',
				'description': 'cabesa',
				'publish_date': '2016-01-01T00:00:00Z',
				'owner': 1}
		response = client.post(reverse('proyectoinicio:testNoticias'), noticia, format='json')
		self.assertEqual(response.status_code, status.HTTP_201_CREATED)
		noticia2 = {'title': 'illotio',
				'description': 'cabesa',
				'publish_date': '2016-01-01T00:00:00Z',
				'owner': 1}
		response = client.patch(reverse('proyectoinicio:testNoticiasB', kwargs={'pk': 1}), noticia2,  format='json')
		self.assertEqual(response.status_code, status.HTTP_200_OK)

	def test_update_evento(self):
		"""
		Verificamos si acepta una peticion PATCH los eventos.
		"""
		usuario = create_user(True)
		client = APIClient()
		client.force_authenticate(usuario)
		evento = {'title': 'illo',
				'description': 'cabesa',
				'start_date': '2016-01-01T00:00:00Z',
				'end_date': '2016-01-05T00:00:00Z',
				'owner': 1}
		response = client.post(reverse('proyectoinicio:testEventos'), evento, format='json')
		self.assertEqual(response.status_code, status.HTTP_201_CREATED)
		evento2 = {'title': 'illotio',
				'description': 'cabesa',
				'start_date': '2016-01-01T00:00:00Z',
				'end_date': '2016-01-05T00:00:00Z',
				'owner': 1}
		# Una vez esté creado el evento podemos ver si se borra.
		response = client.patch(reverse('proyectoinicio:testEventosB', kwargs={'pk': 1}),evento2, format='json')
		# Pasamos como keyword argument 1, ya que como hemos creado un evento la id tendra que ser 1
		self.assertEqual(response.status_code, status.HTTP_200_OK)
