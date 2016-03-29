from django.test import TestCase
from .models import NewsItem, Event
from django.utils import timezone
from django.core.urlresolvers import reverse
from django.test.client import Client
from .forms import NewsItemForm, EventForm

class NewsItemTestCase(TestCase):

	def test_index_v2List(self):
		'''Comprueba el acceso a v2List'''
		response = self.client.get(reverse('NoticiasEventos:v2List'))
		self.assertEqual(response.status_code, 200)
		self.assertContains(response, "No items found.")
		self.assertQuerysetEqual(response.context['result'], [])

	def test_form_isTrue_v2Create_v2Update(self):
		'''Comprobar si el formulario es correcto'''
		form = NewsItemForm(data={'title': 'prueba title', 'description': 'prueba description', 'publish_date': timezone.now()})
		self.assertTrue(form.is_valid())

	def test_form_isFalse_v2Create_v2Update(self):
		'''Comprobar si el formulario no es correcto'''
		form = NewsItemForm(data={'title': '', 'description': '', 'publish_date': timezone.now()})
		self.assertFalse(form.is_valid())

	def test_index_v1List(self):
		'''Comprueba el acceso a v1List'''
		response = self.client.get(reverse('NoticiasEventos:v1List'))
		self.assertEqual(response.status_code, 200)
		self.assertContains(response, "No items found.")
		self.assertQuerysetEqual(response.context['result'], [])

	def test_delete(self):
		'''Comprueba el acceso a index'''
		response = self.client.get(reverse('NoticiasEventos:v2Delete'))
		self.assertEqual(response.status_code, 200)






		
	


