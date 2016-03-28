from django.test import TestCase
from .models import Base, BaseNews, NewsItem, Event
from django.core.urlresolvers import reverse
import datetime
from django.utils import timezone

class NewsItemMethodTests(TestCase):

	def test_ha_pasado_con_noticias_futuras(self):
		"""
		El metodo tendria que devolver falso para noticias cuya
		fecha de publicacion este mas de dos dias en el futuro
		"""

		hora = timezone.now() + datetime.timedelta(days=12)
		noticia_futura = NewsItem(publish_date=hora)
		self.assertEqual(noticia_futura.ha_pasado() , False)

	def test_ha_pasado_con_noticias_pasadas(self):
		"""
		El metodo tendria que devolver verdadero para noticias
		cuya fecha de publicacion fuese mucho mas de dos dias
		en el pasado
		"""

		hora = timezone.now() - datetime.timedelta(days=12)
		noticia_pasada =  NewsItem(publish_date=hora)
		self.assertEqual(noticia_pasada.ha_pasado(), True)


def create_newsitemP(titulo, descripcion, dias):
	"""
	Esto va a publicar una noticia ficticia con los parametros
	titulo y descripcion que le demos. La fecha de publicacion
	sera pasada (los dias)
	"""
	fecha = timezone.now() - datetime.timedelta(days=dias)
	return NewsItem.objects.create(title=titulo, description=descripcion, publish_date=fecha)

class NewsitemViewTests(TestCase):
	def test_index_view_sin_noticias(self):
		"""
		Si no existe ninguna noticia, deberia de aparecer un mensajito
		que nos dijese que no existe ninguna pregunta
		"""

		response =  self.client.get(reverse('proyectoinicio:noticias'))
		self.assertEqual(response.status_code, 200)
		self.assertContains(response, "No hay noticias disponibles") #Lo verifica automaticamente en el HTML
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
		noticia = create_newsitemP(titulo="prueba" , descripcion="prueba de pasado", dias=3)
		response = self.client.get(reverse('proyectoinicio:ampliar', kwargs={'noticia_pk':noticia.id}))
		self.assertEqual(response.status_code, 200)
		self.assertContains(response, "Esta noticia no es reciente. Deberias borrarla.")

	def test_vista_ampliada_sin_noticias(self):
		"""
		Si ampliasemos una noticia sin pasarle ninguna ID tendriamos que tener un error
		de estatus 404.
		"""
		response = self.client.get(reverse('proyectoinicio:ampliar' , kwargs={'noticia_pk' : 11010101010}))
		self.assertEqual(response.status_code, 404)


