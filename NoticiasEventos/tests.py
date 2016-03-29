from django.test import TestCase
from .models import NewsItem, Event
from django.utils import timezone
from django.core.urlresolvers import reverse
from django.test.client import Client
from .forms import NewsItemForm, EventForm
import ipdb

### NOTA: ORGANIZANDO 

class NewsItemTestCase(TestCase):

	def addNewsItem(self):
		form = NewsItemForm(data={'title': 'test title', 
			'description': 'test description', 
			'publish_date': timezone.now()})
		form.is_valid()
		form.save()
		return NewsItem.objects.get(title="test title")

	def test_index_v2List(self):
		'''Comprueba el acceso a v2List'''
		response = self.client.get(reverse('NoticiasEventos:v2List'))
		self.assertEqual(response.status_code, 200)
		self.assertContains(response, "No items found.")
		self.assertQuerysetEqual(response.context['result'], [])

	def test_form_create_newsItem(self):
		'''Comprueba la validacion del formulario y la creacion del objeto NewsItem'''
		form = NewsItemForm(data={'title': 'prueba title', 
			'description': 'prueba description', 
			'publish_date': timezone.now()})
		self.assertTrue(form.is_valid())
		form.save()
		x = NewsItem.objects.get(title="prueba title")
		self.assertTrue(x)

	def test_form_update_newsItem(self):
		'''Comprueba el acceso a v1Update'''
		x = self.addNewsItem()
		response = self.client.get(reverse('NoticiasEventos:v1Update', kwargs = {'pk': x.id}))
		self.assertEqual(response.status_code, 200)
		

	def test_delete_v1(self):
		'''Comprueba si se borra correctamente'''
		x = self.addNewsItem()
		self.client.get(reverse('NoticiasEventos:v1Delete', kwargs = {'pk':x.id}))
		self.assertQuerysetEqual(NewsItem.objects.all(), [])

	def test_index_v1List(self):
		'''Comprueba el acceso a v1List'''
		response = self.client.get(reverse('NoticiasEventos:v1List'))
		self.assertEqual(response.status_code, 200)
		self.assertContains(response, "No items found.")
		self.assertQuerysetEqual(response.context['result'], [])

	
		
