from rest_framework import serializers
from proyectoinicio.models import NewsItem, Base, BaseNews, Event


class NewsItemSerializer(serializers.ModelSerializer):
  class Meta:
    model = NewsItem
    fields = ('id' , 'title', 'description', 'publish_date')