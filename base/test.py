#!/usr/bin/env python
# -*- coding: utf-8 -*-
from django.test import TestCase

from .models import NewsItem, Event
from .forms import EventsForm, NewsForm
from django.core.urlresolvers import reverse
from django.test.client import Client
from rest_framework.test import APIClient
from django.utils import timezone
from datetime import datetime, timedelta
from base.admin import admin
from django.contrib.admin.sites import AdminSite



class NewsItemTests(TestCase):

    def setUp(self):
        NewsItem.objects.create(title="lion", description="roar")

    def createNewsItem(self, title, description):
        form = NewsForm(data={"title": title,
                              "description": description})
        form.is_valid()
        form.save()
        return NewsItem.objects.get(title=title)

    def test_index(self):
        response = self.client.get(reverse("base:index"))
        self.assertEqual(response.status_code, 200)

    def test_index_todo_clases(self):
        response = self.client.get(reverse("base:TodosList"))
        self.assertEqual(response.status_code, 200)

    def test_index_todo_funciones(self):
        response = self.client.get(reverse("base:obtener_todo"))
        self.assertEqual(response.status_code, 200)

    def test_index_todo_clases(self):
        response = self.client.get(reverse("base:TodosList"))
        self.assertEqual(response.status_code, 200)

    def test_index_news(self):
        response = self.client.get(reverse("base:obtener_news"))
        self.assertEqual(response.status_code, 200)

    def test_create_post(self):
        url = reverse("base:create_new")
        data = {"title": "jhbdvf", "description": "kjbfv"}
        c = self.client.post(url, data)
        self.assertEqual(c.status_code, 302)

    def test_update_post(self):
        nuevaNew = NewsItem.objects.get(title="lion")
        url = reverse("base:update_new", kwargs={"new_id": nuevaNew.pk})
        data = {"title": "jhbdvf", "description": "kjbfv"}
        c = self.client.post(url, data)
        self.assertEqual(c.status_code, 302)

    def test_create_new(self):
        response = self.client.get(reverse("base:create_new"))
        self.assertEqual(response.status_code, 200)

    def test_create_new_ok_funciones(self):
        new = self.createNewsItem(title="hjbdsc", description="kjbndfsgbv")
        response = self.client.get(reverse("base:obtener_news"))
        self.assertEqual(response.status_code, 200)

    def test_create_new_no_ok_funciones_clases(self):
        form = NewsForm(data={"title": "", "description": ""})
        self.assertFalse(form.is_valid())

    def test_detail_news_funciones(self):
        nuevaNew = NewsItem.objects.get(title="lion")
        response = self.client.get(reverse("base:detail_news", kwargs={"new_id": nuevaNew.pk}))
        self.assertEqual(response.status_code, 200)

    def test_create_new_ok_clases(self):
        new = self.createNewsItem(title="hjbdsc", description="kjbndfsgbv")
        response = self.client.get(reverse("base:NewsItemList"))
        self.assertEqual(response.status_code, 200)

    def test_update_new_ok_funciones(self):
        nuevaNew = NewsItem.objects.get(title="lion")
        response = self.client.get(reverse("base:update_new", kwargs={"new_id": nuevaNew.pk}))
        self.assertEqual(response.status_code, 200)

    def test_update_new_ok_clases(self):
        nuevaNew = NewsItem.objects.get(title="lion")
        response = self.client.get(reverse("base:NewsUpdate", kwargs={"pk": nuevaNew.pk}))
        self.assertEqual(response.status_code, 200)

    def test_delete_new_ok_funciones(self):
        nuevaNew = NewsItem.objects.get(title="lion")
        response = self.client.get(reverse("base:delete_new", kwargs={"new_id": nuevaNew.pk}))
        self.assertEqual(response.status_code, 200)


class EventTest(TestCase):

    def setUp(self):
        start_date = timezone.now()
        end_date = timezone.now()
        Event.objects.create(title="cat", description="meow", start_date=start_date, end_date=end_date)

    def test_index_events(self):
        response = self.client.get(reverse("base:obtener_events"))
        self.assertEqual(response.status_code, 200)

    def test_create_event_ok_funciones(self):
        time = timezone.now()
        time1 = timedelta(days=5)
        url = reverse("base:create_event")
        data = {"title": "jhbdvf", "description": "kjbfv", "start_date": time, "end_date": time1}
        c = self.client.post(url, data)
        self.assertEqual(c.status_code, 200)

    def test_create_event_no_ok_funciones(self):
        time = timezone.now()
        time1 = timedelta(days=5)
        url = reverse("base:create_event")
        data = {"title": "jhbdvf", "description": "kjbfv", "start_date": time1, "end_date": time}
        c = self.client.post(url, data)
        self.assertEqual(c.status_code, 200)

    def test_detail_events_funciones(self):
        nuevo = Event.objects.get(title="cat")
        response = self.client.get(reverse("base:detail_events", kwargs={"event_id": nuevo.pk}))
        self.assertEqual(response.status_code, 200)

    def test_create_event(self):
        response = self.client.get(reverse("base:create_event"))
        self.assertEqual(response.status_code, 200)

    def test_create_post(self):
        url = reverse("base:create_event")
        data = {"title": "jhbdvf", "description": "kjbfv", "start_date": timezone.now(), "end_date": timezone.now()}
        c = self.client.post(url, data)
        self.assertEqual(c.status_code, 200)

    def test_update_post(self):
        nuevo = Event.objects.get(title="cat")
        url = reverse("base:update_event", kwargs={"event_id": nuevo.pk})
        data = {"title": "jhbdvf", "description": "kjbfv", "start_date": timezone.now(), "end_date": timezone.now()}
        c = self.client.post(url, data)
        self.assertEqual(c.status_code, 200)

    def test_create_event_ok_clases(self):
        time = timezone.now() + timedelta(days=5)
        time1 = timezone.now()
        url = reverse("base:EventsCreate")
        data = {"title": "kjbdfv", "description": "kjbfv", "start_date": time1, "end_date": time}
        response = self.client.post(url, data)
        self.assertEqual(response.status_code, 200)

    def test_create_event_no_ok_clases(self):
        time = timezone.now() + timedelta(days=5)
        time1 = timezone.now()
        url = reverse("base:EventsCreate")
        data = {"title": "kjbdfv", "description": "kjbfv", "start_date": time, "end_date": time1}
        response = self.client.post(url, data)
        self.assertEqual(response.status_code, 200)

    def test_update_event_ok_funciones(self):
        nuevoEvent = Event.objects.get(title="cat")
        response = self.client.get(reverse("base:update_event", kwargs={"event_id": nuevoEvent.pk}))
        self.assertEqual(response.status_code, 200)

    def test_update_event_ok_clases(self):
        nuevoEvent = Event.objects.get(title="cat")
        response = self.client.get(reverse("base:EventsUpdate", kwargs={"pk": nuevoEvent.pk}))
        self.assertEqual(response.status_code, 200)

    def test_delete_event_ok_funciones(self):
        nuevoEvent = Event.objects.get(title="cat")
        response = self.client.get(reverse("base:delete_event", kwargs={"event_id": nuevoEvent.pk}))
        self.assertEqual(response.status_code, 200)

"""API"""


class ApiTest(TestCase):

    def setUp(self):
        start_date = timezone.now()
        end_date = timezone.now()
        Event.objects.create(title="cat", description="meow", start_date=start_date, end_date=end_date)

    def setUp(self):
        publish_date = timezone.now()
        NewsItem.objects.create(title="lion", description="meow", publish_date=publish_date)

    def test_api_view_news(self):
        response = self.client.get(reverse("base:NewsItemListAPI"))
        self.assertEqual(response.status_code, 200)

    def test_api_view_events(self):
        response = self.client.get(reverse("base:EventsListAPI"))
        self.assertEqual(response.status_code, 200)

    def test_api_create_news(self):
        url = reverse("base:NewsItemListAPI")
        data = {"title": "kjbdfv", "description": "kjbfv", "publish_date": "2016-04-04"}
        response = self.client.post(url, data, format='json')
        self.assertEqual(response.status_code, 201)

    def test_api_create_events(self):
        url = reverse("base:EventsListAPI")
        data = {"title": "kjbdfv", "description": "kjbfv", "start_date": "2016-04-04", "end_date": "2016-04-04"}
        response = self.client.post(url, data, format='json')
        self.assertEqual(response.status_code, 201)
